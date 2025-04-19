package com.example.javaHomeworkSecondTerm.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

  @Bean
  public NewTopic auditTopic(@Value("${topic-to-send-message}") String topic) {
    return TopicBuilder.name(topic)
        .partitions(3)
        .replicas(1)
        .compact()
        .build();
  }
}
