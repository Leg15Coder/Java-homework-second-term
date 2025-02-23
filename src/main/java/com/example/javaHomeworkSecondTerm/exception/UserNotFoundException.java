package com.example.javaHomeworkSecondTerm.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Пользователь с ID %d не найден".formatted(id));
    }
}
