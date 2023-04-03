package ru.yandex.practicum.filmorate.exception;

public abstract class EntityNotFoundException extends RuntimeException {
    protected final long id;

    public EntityNotFoundException(String message, long id) {
        super(message);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
