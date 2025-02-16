package com.example.java_homework_second_term.service;

import com.example.java_homework_second_term.exception.UserNotFoundException;
import com.example.java_homework_second_term.model.User;
import com.example.java_homework_second_term.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setEmail(updatedUser.getEmail());
                    existingUser.setName(updatedUser.getName());
                    existingUser.setSurname(updatedUser.getSurname());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User patchUser(Long id, User partialUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    if (partialUser.getEmail() != null) existingUser.setEmail(partialUser.getEmail());
                    if (partialUser.getName() != null) existingUser.setName(partialUser.getName());
                    if (partialUser.getSurname() != null) existingUser.setSurname(partialUser.getSurname());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

