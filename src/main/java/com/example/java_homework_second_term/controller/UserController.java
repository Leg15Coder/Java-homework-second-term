package com.example.java_homework_second_term.controller;

import com.example.java_homework_second_term.model.User;
import com.example.java_homework_second_term.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Collection<User>> getAllUsers() {
        final Collection<User> result = userService.getAllUsers();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        final User result = userService.createUser(user);
        log.info("created %s".formatted(result.toString()));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}

