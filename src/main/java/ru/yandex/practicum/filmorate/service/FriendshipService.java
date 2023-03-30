package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.user.Friendship;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;

import java.util.Collection;
import java.util.Optional;

import static ru.yandex.practicum.filmorate.model.user.EFriendshipStatus.CONFIRMED;

@Service
public class FriendshipService {

    private final FriendshipStorage friendshipStorage;

    @Autowired
    public FriendshipService(FriendshipStorage friendshipStorage) {
        this.friendshipStorage = friendshipStorage;
    }

    public void removeFriend(long userId, long friendId) {
        friendshipStorage.deleteFriendshipByUserIds(userId, friendId);
    }

    public void addFriend(long userId, long friendId) {
        if (friendshipExists(userId, friendId)) return;

        // В тестах, несмотря на тз, предполагается, что подтверждать дружбу не надо
        Friendship firstUserFriendship = new Friendship(userId, friendId, CONFIRMED);
        Friendship secondUserFriendship = new Friendship(friendId, userId, CONFIRMED);

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
        if (firstUserId == secondUserid) return getFriends(firstUserId);
        return friendshipStorage.getMutualFriends(firstUserId, secondUserid);
    }

    private boolean friendshipExists(long userId, long friendId) {
        if (userId == friendId) return true;

        Optional<Friendship> userFriendship = friendshipStorage.
                getFriendshipByUserIds(userId, friendId);

        return userFriendship.isPresent();
    }
}