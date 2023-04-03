package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;

public interface UserStorage extends CreateRetrieveUpdateStorage<User> {
    /**
     * @throws ru.yandex.practicum.filmorate.exception.UserNotFoundException если пользователь с таким id не найден
     */
    @Override
    User update(User user) throws UserNotFoundException;

    boolean existsById(long id);
}