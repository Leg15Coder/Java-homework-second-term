package com.example.javaHomeworkSecondTerm.service;

import com.example.javaHomeworkSecondTerm.exception.UserDeleteException;
import com.example.javaHomeworkSecondTerm.exception.UserNotFoundException;
import com.example.javaHomeworkSecondTerm.repository.UsersRepository;
import com.example.javaHomeworkSecondTerm.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;

    public Collection<User> getAllUsers() {
        return usersRepository.findAll();
    }

  @Cacheable(value = "userCache", key = "#id")
  public User getUserById(Long id) {
    return usersRepository.findById(id).orElse(null);
  }

    public User createUser(User user) {
        return usersRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        return usersRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setEmail(updatedUser.getEmail());
                    existingUser.setName(updatedUser.getName());
                    existingUser.setSurname(updatedUser.getSurname());
                    return usersRepository.save(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User patchUser(Long id, User partialUser) {
        return usersRepository.findById(id)
                .map(existingUser -> {
                    if (partialUser.getEmail() != null) existingUser.setEmail(partialUser.getEmail());
                    if (partialUser.getName() != null) existingUser.setName(partialUser.getName());
                    if (partialUser.getSurname() != null) existingUser.setSurname(partialUser.getSurname());
                    return usersRepository.save(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
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
    public void deleteUser(Long id) {
      User user = usersRepository.findById(id).orElseThrow(() -> new UserDeleteException("%d".formatted(id)));
      usersRepository.delete(user);
    }
}

