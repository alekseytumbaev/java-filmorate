package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Entity;

import java.util.*;

public abstract class AbstractInMemoryStorage<T extends Entity> {
    private long nextId;
    protected final Map<Long, T> entities = new HashMap<>();

    public T add(T entity) {
        entity.setId(getNextId());
        entities.put(entity.getId(), entity);
        return entity;
    }

    public Optional<T> getById(long id) {
        return Optional.ofNullable(entities.get(id));
    }

    public Collection<T> getAll() {
        return entities.values();
    }

    public Collection<T> getAllById(Collection<Long> ids) {
        Collection<T> result = new ArrayList<>(ids.size());
        for (Long id : ids) {
            T entity = entities.get(id);
            if (entity != null)
                result.add(entity);
        }
        return result;
    }

    public boolean existsById(long id) {
        return entities.containsKey(id);
    }

    protected long getNextId() {
        return ++nextId;
    }
}
