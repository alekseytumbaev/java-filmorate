package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage extends AbstractInMemoryStorage<Film> implements FilmStorage {
    @Override
    public Film update(Film entity) {
        long id = entity.getId();
        if (!existsById(id))
            throw new FilmNotFoundException(
                    String.format("Film with id=%d not found", id), id);
        entities.put(id, entity);
        return entity;
    }

    public Collection<Film> getOrderedByLikesAcs(int amount) {
        Comparator<Film> comparator = (f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size());
        return entities.values().stream().sorted(comparator).limit(amount)
                .collect(Collectors.toList());
    }
}