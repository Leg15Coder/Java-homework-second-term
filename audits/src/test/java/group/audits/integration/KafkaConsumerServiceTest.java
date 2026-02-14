package group.audits.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import group.audits.dto.Audit;
import group.audits.service.KafkaConsumerService;
import group.audits.service.UserAuditService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

import java.time.Instant;
import java.util.UUID;

@EmbeddedKafka(partitions = 1, topics = {"audit-topic"})
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KafkaConsumerServiceTest {

  @Autowired
  private KafkaConsumerService kafkaConsumerService;

  @Autowired
  private UserAuditService userAuditService;

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void testConsumeMessage_success() throws Exception {
    Audit audit = new Audit(UUID.randomUUID(), "INSERT", Instant.now(), "User created");
    String message = objectMapper.writeValueAsString(audit);

    kafkaTemplate.send("audit-topic", message);
    Thread.sleep(2000);

    var result = userAuditService.getAllUserActions(audit.userId());
    Assertions.assertFalse(result.isEmpty());
  }

  @Test
  void testConsumeMessage_invalidJson_shouldThrowException() {
    String invalidMessage = "{ invalid json }";

    Assertions.assertThrows(JsonProcessingException.class, () -> {
      kafkaConsumerService.consumeMessage(invalidMessage);
    });
  }
}