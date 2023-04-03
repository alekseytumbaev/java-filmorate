package ru.yandex.practicum.filmorate.exception;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String message, long userId) {
        super(message, userId);
    }
}
