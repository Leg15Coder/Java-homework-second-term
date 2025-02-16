package com.example.java_homework_second_term.service;

import com.example.java_homework_second_term.model.User;
import com.example.java_homework_second_term.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Service
// @RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<User> getAllUsers() {
        return Collections.emptyList();
    }

    public User createUser(User user) {
        return new User();
    }
}

