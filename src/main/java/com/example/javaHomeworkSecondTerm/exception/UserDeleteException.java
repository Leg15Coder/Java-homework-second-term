package com.example.javaHomeworkSecondTerm.exception;

public class UserDeleteException extends RuntimeException {
    public UserDeleteException(String id) {
        super("Не получилось удалить пользователя с id=%s".formatted(id));
    }
}
