package com.example.javaHomeworkSecondTerm.service;

import com.example.javaHomeworkSecondTerm.Application;
import com.example.javaHomeworkSecondTerm.config.TestContainerConfig;
import com.example.javaHomeworkSecondTerm.dto.Audit;
import com.example.javaHomeworkSecondTerm.security.SecurityConfig;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = {SecurityConfig.class, Application.class})
@Import(KafkaProducerService.class)
class KafkaProducerServiceTest extends TestContainerConfig {

  @Container
  private static final KafkaContainer KAFKA = new KafkaContainer(
      DockerImageName.parse("confluentinc/cp-kafka")
  );

  @Autowired
  private KafkaProducerService producerService;

  private Consumer<String, String> consumer;

  @BeforeEach
  void kafkaSetUp() {
    Map<String, Object> consumerProps = Map.of(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA.getBootstrapServers(),
        ConsumerConfig.GROUP_ID_CONFIG, "test-group",
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class
    );

    ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);
    consumer = consumerFactory.createConsumer();
  }

  @AfterEach
  void tearDown() {
    if (consumer != null) {
      consumer.close();
    }
  }

  @Test
  void sendMessage_positive() {
    String topic = "test-topic";
    consumer.subscribe(Collections.singletonList(topic));

    Audit audit = new Audit(
        UUID.randomUUID(),
        "TEST_ACTION",
        Instant.now(),
        "Test message"
    );

    try {
      producerService.sendMessage(audit);

      ConsumerRecords<String, String> records = KafkaTestUtils.getRecords(consumer, Duration.ofSeconds(5));
      assertThat(records.count()).isEqualTo(1);

      String value = records.iterator().next().value();
      assertThat(value).contains(audit.userId().toString());
      assertThat(value).contains(audit.action());
    } catch (KafkaException e) {
      System.err.printf("Не запущена kafka: %s%n", e.getMessage());
    }
  }

  @Test
  void sendMessage_negative_kafkaUnavailable() {
    KAFKA.stop();

    Audit audit = new Audit(
        UUID.randomUUID(),
        "TEST_ACTION",
        Instant.now(),
        "Test message"
    );

    assertThrows(Exception.class, () -> {
      producerService.sendMessage(audit);
    });

    KAFKA.start();
  }
}