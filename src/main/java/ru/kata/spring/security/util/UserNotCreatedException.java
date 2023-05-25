package ru.kata.spring.security.util;
// если не удалось создать пользователя
public class UserNotCreatedException extends RuntimeException {
    public UserNotCreatedException(String message) {
        super(message);
    }

}
