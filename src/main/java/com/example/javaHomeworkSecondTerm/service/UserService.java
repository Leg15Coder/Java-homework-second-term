package com.example.javaHomeworkSecondTerm.service;

import com.example.javaHomeworkSecondTerm.dto.Audit;
import com.example.javaHomeworkSecondTerm.exception.UserDeleteException;
import com.example.javaHomeworkSecondTerm.exception.UserNotFoundException;
import com.example.javaHomeworkSecondTerm.model.OutboxRecord;
import com.example.javaHomeworkSecondTerm.repository.OutboxRepository;
import com.example.javaHomeworkSecondTerm.repository.UsersRepository;
import com.example.javaHomeworkSecondTerm.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final UsersRepository usersRepository;
  private final KafkaProducerService kafkaProducerService;
  private final OutboxRepository outboxRepository;
  private final ObjectMapper objectMapper;

  public Collection<User> getAllUsers(UUID userId) {
    var users = usersRepository.findAll();
    try {
      outboxRepository.save(new OutboxRecord(
          objectMapper.writeValueAsString(
              new Audit(userId, "SELECT", Instant.now(), "select all users")
          )
      ));
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }
    return users;
  }

  @Cacheable(value = "userCache", key = "#id")
  public User getUserById(UUID userId, UUID id) {
    var user = usersRepository.findById(id).orElse(null);
    try {
      outboxRepository.save(new OutboxRecord(
          objectMapper.writeValueAsString(
              new Audit(userId, "SELECT", Instant.now(), "select user with id=%s".formatted(id))
          )
      ));
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }
    return user;
  }

  public User createUser(UUID userId, User user) {
    var result = usersRepository.save(user);
    try {
      outboxRepository.save(new OutboxRecord(
          objectMapper.writeValueAsString(
              new Audit(userId, "INSERT", Instant.now(), "create new user")
          )
      ));
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }
    return result;
  }

  public User updateUser(UUID userId, UUID id, User updatedUser) {
    var user = usersRepository.findById(id)
        .map(existingUser -> {
          existingUser.setEmail(updatedUser.getEmail());
          existingUser.setName(updatedUser.getName());
          existingUser.setSurname(updatedUser.getSurname());
          return usersRepository.save(existingUser);
        })
        .orElseThrow(() -> new UserNotFoundException(id));

    try {
      outboxRepository.save(new OutboxRecord(
          objectMapper.writeValueAsString(
              new Audit(userId, "UPDATE", Instant.now(), "update user with id=%s".formatted(id))
          )
      ));
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }

    return user;
  }

  public User patchUser(UUID userId, UUID id, User partialUser) {
    var user = usersRepository.findById(id)
        .map(existingUser -> {
          if (partialUser.getEmail() != null) existingUser.setEmail(partialUser.getEmail());
          if (partialUser.getName() != null) existingUser.setName(partialUser.getName());
          if (partialUser.getSurname() != null) existingUser.setSurname(partialUser.getSurname());
          return usersRepository.save(existingUser);
        })
        .orElseThrow(() -> new UserNotFoundException(id));

    try {
      outboxRepository.save(new OutboxRecord(
          objectMapper.writeValueAsString(
              new Audit(userId, "UPDATE", Instant.now(), "patch user with id=%s".formatted(id))
          )
      ));
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }

    return user;
  }

  /**
   * Метод для удаления пользователя с добавлением механизма повторных попыток.
   * В случае возникновения исключения UserDeleteException удаление будет пытаться повториться
   * до 5 раз с интервалом в 10 секунд. Это гарантия того, что при временных сбоях (например,
   * проблемы с соединением или с базой данных) операция удаления будет повторена.
   *
   * @param id ID пользователя, которого нужно удалить.
   */
  @Retryable(value = UserDeleteException.class, maxAttempts = 5, backoff = @Backoff(delay = 10000))
  public void deleteUser(UUID userId, UUID id) {
    User user = usersRepository.findById(id).orElseThrow(() -> new UserDeleteException("%s".formatted(id)));
    usersRepository.delete(user);

    try {
      outboxRepository.save(new OutboxRecord(
          objectMapper.writeValueAsString(
              new Audit(userId, "DELETE", Instant.now(), "delete user with id=%s".formatted(id))
          )
      ));
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }
  }
}

