package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {
    private final UserStorage userStorage;
    private final FriendshipService friendshipService;

    @Autowired
    public UserService(UserStorage userStorage, FriendshipService friendshipService) {
        this.userStorage = userStorage;
        this.friendshipService = friendshipService;
    }

    public void addFriend(long userId, long newFriendId) {
        if (userId == newFriendId) return;

        User user = getByIdIfExists(userId);
        User friend = getByIdIfExists(newFriendId);

        friendshipService.addFriend(user, friend);
    }

    public void removeFriend(long userId, long friendToRemoveId) {
        if (userId == friendToRemoveId) return;

        User user = getByIdIfExists(userId);
        User friend = getByIdIfExists(friendToRemoveId);

        friendshipService.removeFriend(user, friend);
    }

    public Collection<User> getFriends(long userId) {
        Collection<Long> friendIds = friendshipService.getFriendIds(userId);
        return userStorage.getAllById(friendIds);
    }

    /**
     * Если firstUserId = secondUserId - возвращается просто список друзей этого пользователя
     */
    public Collection<User> getMutualFriends(long firstUserId, long secondUserId) {
        Collection<Long> firstUserFriendIds = friendshipService.getFriendIds(firstUserId);
        Collection<Long> secondUserFriendIds = friendshipService.getFriendIds(secondUserId);

        firstUserFriendIds.retainAll(secondUserFriendIds);

        return userStorage.getAllById(firstUserFriendIds);
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