package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

@Component
public class InMemoryUserStorage extends AbstractInMemoryStorage<User> implements UserStorage {

    @Override
    public User update(User entity) {
        long id = entity.getId();
        if (!existsById(id))
            throw new UserNotFoundException(
                    String.format("User with id=%d not found", id), id);
        entities.put(id, entity);
        return entity;
    }
}
