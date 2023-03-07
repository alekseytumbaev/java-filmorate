package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage extends Storage<User> {

    /**
     * @throws ru.yandex.practicum.filmorate.exception.UserNotFoundException если пользователь с таким id не найден
     */
    @Override
    User update(User user);
}