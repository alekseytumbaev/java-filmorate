package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {

    User add(User user);

    /**
     *
     * @throws ru.yandex.practicum.filmorate.exception.UserNotFoundException если пользователь с таким id не найден
     */
    User update(User user);

    Optional<User> getById(long id);

    Collection<User> getAll();

    /**
     * Если передано несуществующее id - оно игнорируется
     */
    Collection<User> getAllById(Collection<Long> ids);

    boolean existsById(long id);
}