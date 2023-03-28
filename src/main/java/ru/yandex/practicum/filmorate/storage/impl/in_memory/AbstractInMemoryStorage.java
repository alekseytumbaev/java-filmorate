package ru.yandex.practicum.filmorate.storage.impl.in_memory;

import ru.yandex.practicum.filmorate.model.Entity;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.*;

public abstract class AbstractInMemoryStorage<T extends Entity> implements Storage<T> {
    private long nextId;
    protected final Map<Long, T> entities = new HashMap<>();

    @Override
    public T add(T entity) {
        entity.setId(getNextId());
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<T> getById(long id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Collection<T> getAll() {
        return entities.values();
    }

    @Override
    public Collection<T> getAllById(Collection<Long> ids) {
        Collection<T> result = new ArrayList<>(ids.size());
        for (Long id : ids) {
            T entity = entities.get(id);
            if (entity != null)
                result.add(entity);
        }
        return result;
    }

    @Override
    public boolean existsById(long id) {
        return entities.containsKey(id);
    }

    protected long getNextId() {
        return ++nextId;
    }
}
