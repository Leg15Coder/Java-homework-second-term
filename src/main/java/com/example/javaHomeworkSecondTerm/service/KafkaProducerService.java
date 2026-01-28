package com.example.javaHomeworkSecondTerm.service;


import com.example.javaHomeworkSecondTerm.dto.Audit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class KafkaProducerService {
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;
  private final String topic;

  public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate,
                              ObjectMapper objectMapper,
                              @Value("{topic-to-send-message}") String topic) {
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
    this.topic = topic;
  }

  public void sendMessage(Audit dtoMessage) {
    String message = "";
    try {
      message = objectMapper.writeValueAsString(dtoMessage);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }

    CompletableFuture<SendResult<String, String>> sendResult = kafkaTemplate.send(topic, message);
  }
}

