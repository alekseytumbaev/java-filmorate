package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.Collection;

public interface GenreStorage {

    Collection<Genre> getGenresByFilmId(long filmId);
}
