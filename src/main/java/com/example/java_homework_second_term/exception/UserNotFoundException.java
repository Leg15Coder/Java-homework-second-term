package com.example.java_homework_second_term.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Пользователь с ID %d не найден".formatted(id));
    }
}
