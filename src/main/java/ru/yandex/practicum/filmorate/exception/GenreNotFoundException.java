package ru.yandex.practicum.filmorate.exception;

public class GenreNotFoundException extends EntityNotFoundException {
    public GenreNotFoundException(String message, long id) {
        super(message, id);
    }
}
