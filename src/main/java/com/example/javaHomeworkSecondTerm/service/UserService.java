package com.example.javaHomeworkSecondTerm.service;

import com.example.javaHomeworkSecondTerm.exception.UserNotFoundException;
import com.example.javaHomeworkSecondTerm.repository.UsersRepository;
import com.example.javaHomeworkSecondTerm.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;

    public Collection<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return usersRepository.findById(id);
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

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}

