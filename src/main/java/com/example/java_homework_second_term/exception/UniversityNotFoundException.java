package com.example.java_homework_second_term.exception;

public class UniversityNotFoundException extends RuntimeException {
    public UniversityNotFoundException(Long id) {
        super("Университет с ID %d не найден".formatted(id));
    }
}
