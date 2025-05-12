package com.example.javaHomeworkSecondTerm.service;

import com.example.javaHomeworkSecondTerm.model.OutboxRecord;
import com.example.javaHomeworkSecondTerm.repository.OutboxRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class OutboxScheduler {
  private final Timer processingTimer;
  private final MeterRegistry meterRegistry;
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final String topic;
  private final OutboxRepository outboxRepository;

  public OutboxScheduler(
      MeterRegistry meterRegistry,
      KafkaTemplate<String, String> kafkaTemplate,
      OutboxRepository outboxRepository,
      @Value("${topic-to-send-message}") String topic) {

    this.processingTimer = Timer.builder("outbox.process.timer")
        .description("Время, затраченное на обработку исходящих сообщений")
        .publishPercentiles(0.5, 0.75, 0.95, 0.99)
        .publishPercentileHistogram()
        .register(meterRegistry);

    this.meterRegistry = meterRegistry;
    this.kafkaTemplate = kafkaTemplate;
    this.outboxRepository = outboxRepository;
    this.topic = topic;
  }

  @Transactional
  @Scheduled(fixedDelay = 10000)
  public void processOutbox() {
    processingTimer.record(() -> {
      List<OutboxRecord> records = outboxRepository.findAll();
      for (OutboxRecord record : records) {
        try {
          SendResult<String, String> sendResult = kafkaTemplate.send(topic, record.getData())
              .get(10, TimeUnit.SECONDS);
          outboxRepository.delete(record);

          meterRegistry.counter("outbox.process.result.count", "status", "success").increment();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
          meterRegistry.counter("outbox.process.result.count", "status", "error").increment();
        }
      }
    });
  }
}

