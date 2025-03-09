package com.example.javaHomeworkSecondTerm.aspect;

import com.example.javaHomeworkSecondTerm.controller.UsersController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LoggingAspectTest {

  @Autowired
  private LoggingAspect loggingAspect;

  @Autowired
  private UsersController usersController;

  @Test
  void testAspectCounter() {
    int initialCount = loggingAspect.getCounter();

    usersController.getAllUsers();

    assertEquals(initialCount + 2, loggingAspect.getCounter(), "Счётчик аспекта должен увеличиться на 2");
  }
}


