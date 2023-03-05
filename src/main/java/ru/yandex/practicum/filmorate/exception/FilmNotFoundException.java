package ru.yandex.practicum.filmorate.exception;

public class FilmNotFoundException extends RuntimeException {
    private final long filmId;

    public FilmNotFoundException(String message, long filmId) {
        super(message);
        this.filmId = filmId;
    }

    public long getFilmId() {
        return filmId;
    }
}
