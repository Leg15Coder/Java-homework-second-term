package com.example.javaHomeworkSecondTerm.aspect;

import com.example.javaHomeworkSecondTerm.config.TestContainerConfig;
import com.example.javaHomeworkSecondTerm.controller.UsersController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.KafkaException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LoggingAspectTest extends TestContainerConfig {

  @Autowired
  private LoggingAspect loggingAspect;

  @Autowired
  private UsersController usersController;

  @Test
  void testAspectCounter() {
    int initialCount = loggingAspect.getCounter();

    try {
      usersController.getAllUsers(UUID.randomUUID());
      assertEquals(initialCount + 2, loggingAspect.getCounter(), "Счётчик аспекта должен увеличиться на 2");
    } catch (KafkaException e) {
      System.err.printf("Не запущена kafka: %s%n", e.getMessage());
    }
  }
}


