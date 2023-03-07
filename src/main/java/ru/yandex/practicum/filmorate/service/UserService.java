package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(long userId, long newFriendId) {
        if (userId == newFriendId) return;

        User user = getByIdIfExists(userId);
        User friend = getByIdIfExists(newFriendId);

        user.getFriendIds().add(friend.getId());
        friend.getFriendIds().add(user.getId());

        update(user);
        update(friend);
    }

    public void removeFriend(long userId, long friendIdToRemove) {
        if (userId == friendIdToRemove) return;

        User user = getByIdIfExists(userId);
        User friend = getByIdIfExists(friendIdToRemove);

        user.getFriendIds().remove(friendIdToRemove);
        friend.getFriendIds().remove(userId);

        update(user);
        update(friend);
    }

    public Collection<User> getFriends(long userId) {
        User user = getByIdIfExists(userId);
        return userStorage.getAllById(user.getFriendIds());
    }

    /**
     * Если firstUserId = secondUserId - возвращается просто список друзей этого пользователя
     */
    public Collection<User> getMutualFriends(long firstUserId, long secondUserId) {
        User firstUser = getByIdIfExists(firstUserId);
        User secondUser = getByIdIfExists(secondUserId);

        Set<Long> mutualFriendIds = new HashSet<>(firstUser.getFriendIds());
        mutualFriendIds.retainAll(secondUser.getFriendIds());

        return userStorage.getAllById(mutualFriendIds);
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

    public User getByIdIfExists(long id) {
        Optional<User> userOpt = userStorage.getById(id);
        if (userOpt.isEmpty())
            throw new UserNotFoundException(
                    String.format("User with id=%d not found", id), id);

        return userOpt.get();
    }
}