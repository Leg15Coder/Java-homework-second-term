package com.example.javaHomeworkSecondTerm.service;

import com.example.javaHomeworkSecondTerm.model.OutboxRecord;
import com.example.javaHomeworkSecondTerm.repository.OutboxRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@Import(OutboxScheduler.class)
@ActiveProfiles("test")
public class OutboxSchedulerIntegrationTest {

  @MockBean
  private KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  private OutboxRepository outboxRepository;

  @Autowired
  private OutboxScheduler scheduler;

  private final String topic = "test-outbox-topic";

  @Test
  void testProcessOutbox_Positive() throws ExecutionException, InterruptedException {
    OutboxRecord record = new OutboxRecord("test-message");
    outboxRepository.save(record);

    CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
    SendResult<String, String> result = mock(SendResult.class);
    future.complete(result);
    when(kafkaTemplate.send(anyString(), anyString())).thenReturn(future);

    scheduler.processOutbox();

    List<OutboxRecord> remaining = outboxRepository.findAll();
    assertThat(remaining).isEmpty();
    verify(kafkaTemplate, times(1)).send(eq(topic), eq("test-message"));
  }

  @Test
  void testProcessOutbox_Negative_KafkaFails() {
    OutboxRecord record = new OutboxRecord("fail-message");
    outboxRepository.save(record);

    CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
    future.completeExceptionally(new RuntimeException("Kafka error"));
    when(kafkaTemplate.send(anyString(), anyString())).thenReturn(future);

    scheduler.processOutbox();

    List<OutboxRecord> remaining = outboxRepository.findAll();
    assertThat(remaining).hasSize(1);
    verify(kafkaTemplate, times(1)).send(eq(topic), eq("fail-message"));
  }
}
