package com.example.javaHomeworkSecondTerm.repositoty;

import com.example.javaHomeworkSecondTerm.config.TestContainerConfig;
import com.example.javaHomeworkSecondTerm.model.User;
import com.example.javaHomeworkSecondTerm.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(TestContainerConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest extends TestContainerConfig {

  @Autowired
  private UsersRepository usersRepository;

  @Test
  void shouldSaveAndFindUserByUsername() {
    User user = new User("email@email.com", "testuser", "somesurname");

    usersRepository.save(user);

    Optional<User> foundUser = usersRepository.findById(user.getId());
    assertThat(foundUser).isPresent();
    assertThat(foundUser.get().getName()).isEqualTo("testuser");
  }

  @Test
  void shouldReturnEmptyIfUserNotFound() {
    Optional<User> foundUser = usersRepository.findById(UUID.randomUUID());
    assertThat(foundUser).isEmpty();
  }
}

