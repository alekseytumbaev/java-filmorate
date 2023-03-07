package ru.yandex.practicum.filmorate.exception;

public class UserNotFoundException extends RuntimeException{
    private final long userId;

    public UserNotFoundException(String message, long userId) {
        super(message);
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }
}
