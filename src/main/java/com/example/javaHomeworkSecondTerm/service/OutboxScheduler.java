package com.example.javaHomeworkSecondTerm.service;

import com.example.javaHomeworkSecondTerm.model.OutboxRecord;
import com.example.javaHomeworkSecondTerm.repository.OutboxRepository;
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
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final String topic;
  private final OutboxRepository outboxRepository;

  public OutboxScheduler(KafkaTemplate<String, String> kafkaTemplate, OutboxRepository outboxRepository, @Value("${topic-to-send-message}") String topic) {
    this.kafkaTemplate = kafkaTemplate;
    this.outboxRepository = outboxRepository;
    this.topic = topic;
  }

  @Transactional
  @Scheduled(fixedDelay = 10000)
  public void processOutbox() {
    List<OutboxRecord> records = outboxRepository.findAll();
    for (OutboxRecord record : records) {
      try {
        SendResult<String, String> sendResult = kafkaTemplate.send(topic, record.getData())
            .get(10, TimeUnit.SECONDS);
      } catch (InterruptedException | ExecutionException | TimeoutException e) {
        continue;
      }

      outboxRepository.delete(record);
    }
  }
}

