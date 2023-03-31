package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.Collection;

public interface FilmStorage extends CreateRetrieveUpdateStorage<Film> {

    /**
     * @throws ru.yandex.practicum.filmorate.exception.FilmNotFoundException если фильм с таким id не найден
     */
    @Override
    Film update(Film film) throws FilmNotFoundException;

    Collection<Film> getOrderedByLikesDesc(int amount);

    boolean existsById(long id);
}
