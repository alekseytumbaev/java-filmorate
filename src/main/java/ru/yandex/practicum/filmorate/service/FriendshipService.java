package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.user.Friendship;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;

import java.util.Collection;
import java.util.Optional;

import static ru.yandex.practicum.filmorate.model.user.EFriendshipStatus.CONFIRMED;
import static ru.yandex.practicum.filmorate.model.user.EFriendshipStatus.RECIEVED;

@Service
public class FriendshipService {

    private final UserService userService;
    private final FriendshipStorage friendshipStorage;

    @Autowired
    public FriendshipService(UserService userService, FriendshipStorage friendshipStorage) {
        this.userService = userService;
        this.friendshipStorage = friendshipStorage;
    }

    public void removeFriend(long userId, long friendId) {
        friendshipStorage.deleteFriendshipByUserIds(userId, friendId);
    }

    public void addFriend(long userId, long friendId) {
        if (!userService.existsById(userId))
            throw new UserNotFoundException(
                    String.format("Cannot add friend with id=%d to user with id=%d, because user not found", friendId, userId),
                    userId);
        if (!userService.existsById(friendId))
            throw new UserNotFoundException(
                    String.format("Cannot add friend with id=%d to user with id=%d, because friend not found", friendId, userId),
                    friendId);

        if (friendshipExists(userId, friendId)) return;

        Friendship firstUserFriendship = new Friendship(userId, friendId, CONFIRMED);
        Friendship secondUserFriendship = new Friendship(friendId, userId, RECIEVED);

        friendshipStorage.add(firstUserFriendship);
        friendshipStorage.add(secondUserFriendship);
    }

    public Collection<User> getFriends(long userId) {
        return friendshipStorage.getFriendsByUserIdAndFriendshipStatus(userId, CONFIRMED);
    }

    /**
     * Если firstUserId = secondUserId - возвращается просто список друзей этого пользователя
     */
    public Collection<User> getMutualFriends(long firstUserId, long secondUserid) {
        if (!userService.existsById(firstUserId))
            throw new UserNotFoundException(
                    String.format("Cannot get mutual friends, because user with id=%d not found", firstUserId),
                    firstUserId);
        if (!userService.existsById(secondUserid))
            throw new UserNotFoundException(
                    String.format("Cannot get mutual friends, because user with id=%d not found", secondUserid),
                    secondUserid);

        if (firstUserId == secondUserid) return getFriends(firstUserId);
        return friendshipStorage.getMutualFriends(firstUserId, secondUserid);
    }

    private boolean friendshipExists(long userId, long friendId) {
        if (userId == friendId) return true;

        Optional<Friendship> userFriendship = friendshipStorage
                .getFriendshipByUserIds(userId, friendId);

        return userFriendship.isPresent();
    }
}