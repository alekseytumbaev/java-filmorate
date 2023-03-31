package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.util.Collection;
import java.util.Optional;

@Service
public class FilmService {
    private static final int DEFAULT_MOST_POPULAR_FILMS_AMOUNT = 10;
    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;
    private final UserService userService;

    @Autowired
    public FilmService(@Qualifier("filmDaoStorage") FilmStorage filmStorage, LikeStorage likeStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
        this.userService = userService;
}

    public Collection<Film> getTopMostPopular(int amount) {
        amount = amount < 1 ? DEFAULT_MOST_POPULAR_FILMS_AMOUNT : amount;

        return filmStorage.getOrderedByLikesDesc(amount);
    }

    public void like(long filmId, long userId) {
        if (!filmStorage.existsById(filmId))
            throw new FilmNotFoundException(
                    String.format("Cannot like film with id=%d, because film not found", filmId), filmId);
        if (!userService.existsById(userId))
            throw new UserNotFoundException(
                    String.format("User with id=%d cannot like film, because user not found", userId), userId);

        likeStorage.addLike(userId, filmId);
    }

    public void removeLike(long filmId, long userId) {
        if (!filmStorage.existsById(filmId))
            throw new FilmNotFoundException(
                    String.format("Cannot remove lke from film with id=%d, because film not found", filmId), filmId);
        if (!userService.existsById(userId))
            throw new UserNotFoundException(
                    String.format("User with id=%d cannot remove like from film, because user not found", userId), userId);

        likeStorage.removeLike(userId, filmId);
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