package com.example.javaHomeworkSecondTerm.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID id) {
        super("Пользователь с ID %d не найден".formatted(id));
    }
}
