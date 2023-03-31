package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.Collection;
import java.util.Optional;

public interface GenreStorage {

    Collection<Genre> getGenresByFilmId(long filmId);

    Collection<Genre> getAll();

    Optional<Genre> getById(long id);
}
