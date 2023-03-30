package ru.yandex.practicum.filmorate.storage.impl.in_memory;

import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Entity;
import ru.yandex.practicum.filmorate.storage.CreateRetrieveUpdateStorage;

import java.util.*;

public abstract class AbstractInMemoryStorage<T extends Entity> implements CreateRetrieveUpdateStorage<T> {
    private long nextId;
    protected final Map<Long, T> entities = new HashMap<>();

    @Override
    public T add(T entity) {
        entity.setId(getNextId());
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public abstract T update(T entity) throws EntityNotFoundException;

    @Override
    public Optional<T> getById(long id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Collection<T> getAll() {
        return entities.values();
    }

    public boolean existsById(long id) {
        return entities.containsKey(id);
    }

    protected long getNextId() {
        return ++nextId;
    }
}
