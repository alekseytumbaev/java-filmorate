package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage extends AbstractInMemoryStorage<Film> implements FilmStorage {
    @Override
    public Film update(Film entity) {
        if (!existsById(entity.getId()))
            throw new FilmNotFoundException(String.format("Film with id=%d not found", entity.getId()));
        entities.put(entity.getId(), entity);
        return entity;
    }

    public Collection<Film> getOrderedByLikesAcs(int amount) {
        Comparator<Film> comparator = (f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size());
        return entities.values().stream().sorted(comparator).limit(amount)
                .collect(Collectors.toList());
    }
}