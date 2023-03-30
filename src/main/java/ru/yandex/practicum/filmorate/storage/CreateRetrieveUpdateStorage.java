package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Entity;

import java.util.Collection;
import java.util.Optional;

public interface CreateRetrieveUpdateStorage<T extends Entity> {
    T add(T entity);

    Optional<T> getById(long id);

    Collection<T> getAll();

    T update(T entity) throws EntityNotFoundException;
}
