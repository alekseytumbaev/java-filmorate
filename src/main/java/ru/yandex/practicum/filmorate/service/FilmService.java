package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.Optional;

@Service
public class FilmService {
    private static final int DEFAULT_MOST_POPULAR_FILMS_AMOUNT = 10;
    private final FilmStorage filmStorage;
    private final UserService userService;

    @Autowired
    public FilmService(@Qualifier("filmDaoStorage") FilmStorage filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
}

    public Collection<Film> getTopMostPopular(int amount) {
        amount = amount < 1 ? DEFAULT_MOST_POPULAR_FILMS_AMOUNT : amount;

        return filmStorage.getOrderedByLikesDesc(amount);
    }

    public void like(long filmId, long userId) {
        Film film = getById(filmId);
        User user = userService.getById(userId);

        film.getLikes().add(user.getId());
        update(film);
    }

    public void removeLike(long filmId, long userId) {
        Film film = getById(filmId);
        User user = userService.getById(userId);

        film.getLikes().remove(user.getId());
        update(film);
    }

    public Film add(Film film) {
        return filmStorage.add(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public Collection<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film getById(long id) {
        Optional<Film> filmOpt = filmStorage.getById(id);
        if (filmOpt.isEmpty())
            throw new FilmNotFoundException(String.format("Film with id=%d not found", id), id);

        return filmOpt.get();
    }
}