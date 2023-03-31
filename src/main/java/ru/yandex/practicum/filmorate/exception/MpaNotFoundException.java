package ru.yandex.practicum.filmorate.exception;

public class MpaNotFoundException extends EntityNotFoundException{
    public MpaNotFoundException(String message, long id) {
        super(message, id);
    }
}
