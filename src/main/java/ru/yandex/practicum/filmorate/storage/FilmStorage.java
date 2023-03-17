package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.Collection;

public interface FilmStorage extends Storage<Film> {

    /**
     * @throws ru.yandex.practicum.filmorate.exception.FilmNotFoundException если фильм с таким id не найден
     */
    @Override
    Film update(Film entity);

    Collection<Film> getOrderedByLikesAcs(int amount);
}
