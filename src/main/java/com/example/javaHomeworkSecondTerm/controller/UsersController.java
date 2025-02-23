package com.example.javaHomeworkSecondTerm.controller;

import com.example.javaHomeworkSecondTerm.api.UserApi;
import com.example.javaHomeworkSecondTerm.model.User;
import com.example.javaHomeworkSecondTerm.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController implements UserApi {
    private final UserService userService;

    @Override
    public ResponseEntity<Collection<User>> getAllUsers() {
        final Collection<User> result = userService.getAllUsers();
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<User> getUserById(Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        final User result = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<User> updateUser(Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @Override
    public ResponseEntity<User> patchUser(Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.patchUser(id, user));
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

