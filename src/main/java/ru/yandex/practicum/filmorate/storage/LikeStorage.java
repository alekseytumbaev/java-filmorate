package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;

public interface LikeStorage {

    void addLike(long userId, long filmId) throws UserNotFoundException, FilmNotFoundException;

    void removeLike(long userId, long filmId);
}
