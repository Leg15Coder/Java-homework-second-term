package com.example.javaHomeworkSecondTerm.controller;

import com.example.javaHomeworkSecondTerm.api.UserApi;
import com.example.javaHomeworkSecondTerm.model.User;
import com.example.javaHomeworkSecondTerm.service.UserService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController implements UserApi {
    private final CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("coursesCircuitBreakerController");
    private final RateLimiter rateLimiter = RateLimiter.ofDefaults("universityRateController");
    private final UserService userService;

    @Override
    public ResponseEntity<Collection<User>> getAllUsers() {
        return circuitBreaker.executeSupplier(() -> {
            return rateLimiter.executeSupplier(() -> {
                final Collection<User> result = userService.getAllUsers();
                return ResponseEntity.ok(result);
            });
        });
    }

    @Override
    public ResponseEntity<User> getUserById(UUID id) {
        return circuitBreaker.executeSupplier(() -> {
            return rateLimiter.executeSupplier(() -> {
                return ResponseEntity.ok(userService.getUserById(id));
            });
        });
    }

    @Override
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return circuitBreaker.executeSupplier(() -> {
            return rateLimiter.executeSupplier(() -> {
                final User result = userService.createUser(user);
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            });
        });
    }

    @Override
    public ResponseEntity<User> updateUser(UUID id, @RequestBody User user) {
        return circuitBreaker.executeSupplier(() -> {
            return rateLimiter.executeSupplier(() -> {
                return ResponseEntity.ok(userService.updateUser(id, user));
            });
        });
    }

    @Override
    public ResponseEntity<User> patchUser(UUID id, @RequestBody User user) {
        return circuitBreaker.executeSupplier(() -> {
            return rateLimiter.executeSupplier(() -> {
                return ResponseEntity.ok(userService.patchUser(id, user));
            });
        });
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID id) {
        return circuitBreaker.executeSupplier(() -> {
            return rateLimiter.executeSupplier(() -> {
                userService.deleteUser(id);
                return ResponseEntity.noContent().build();
            });
        });
    }
}

