package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDaoStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User add(User user) {
        setLoginForEmptyName(user);
        return userStorage.add(user);
    }

    public User update(User user) {
        setLoginForEmptyName(user);
        return userStorage.update(user);
    }

    public Collection<User> getAll() {
        return userStorage.getAll();
    }

    private void setLoginForEmptyName(User user) {
        String name = user.getName();
        if (name == null || name.isBlank())
            user.setName(user.getLogin());
    }

    public User getById(long id) throws UserNotFoundException {
        Optional<User> userOpt = userStorage.getById(id);
        if (userOpt.isEmpty())
            throw new UserNotFoundException(
                    String.format("User with id=%d not found", id), id);

        return userOpt.get();
    }
}