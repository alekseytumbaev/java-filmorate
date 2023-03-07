package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Entity;

import java.util.Collection;
import java.util.Optional;

public interface Storage<T extends Entity> {
    
    T add(T entity);

    T update(T entity);

    Optional<T> getById(long id);

    Collection<T> getAll();

    /**
     * Если передано несуществующее id - оно игнорируется
     */
    Collection<T> getAllById(Collection<Long> ids);

    boolean existsById(long id);
}
