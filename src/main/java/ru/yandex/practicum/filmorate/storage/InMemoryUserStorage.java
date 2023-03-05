package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

@Component
public class InMemoryUserStorage extends AbstractInMemoryStorage<User> implements UserStorage {

    @Override
    public User update(User entity) {
        if (!existsById(entity.getId()))
            throw new UserNotFoundException(String.format("User with id=%d not found", entity.getId()));
        entities.put(entity.getId(), entity);
        return entity;
    }
}
