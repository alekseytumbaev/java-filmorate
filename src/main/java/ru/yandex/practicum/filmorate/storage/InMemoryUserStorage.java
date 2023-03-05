package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private long nextId;
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User add(User user) {
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        if (!existsById(user.getId()))
            throw new UserNotFoundException(String.format("User with id=%d not found", user.getId()));
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> getById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public Collection<User> getAllById(Collection<Long> ids) {
        Collection<User> result = new ArrayList<>(ids.size());
        for (Long id : ids) {
            User user = users.get(id);
            if (user != null)
                result.add(user);
        }
        return result;
    }

    @Override
    public boolean existsById(long id) {
        return users.containsKey(id);
    }

    private long getNextId() {
        return ++nextId;
    }
}
