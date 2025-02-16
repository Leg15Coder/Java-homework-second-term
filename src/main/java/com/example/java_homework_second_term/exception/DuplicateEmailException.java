package com.example.java_homework_second_term.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("Пользователь с email %s уже существует".formatted(email));
    }
}
