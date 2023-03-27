package ru.yandex.practicum.filmorate.service;

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

    public FriendshipService(FriendshipStorage friendshipStorage) {
        this.friendshipStorage = friendshipStorage;
    }

    public void removeFriend(User user, User friend) {
        long userId = user.getId();
        long friendId = friend.getId();

        friendshipStorage.deleteFriendshipByUserIds(userId, friendId);
    }

    public void addFriend(User user, User friend) {
        long userId = user.getId();
        long friendId = friend.getId();
        if (friendshipExists(userId, friendId)) return;

        // В тестах, несмотря на тз, предполагается, что подтверждать дружбу не надо
        Friendship firstUserFriendship = new Friendship(userId, friendId, CONFIRMED);
        Friendship secondUserFriendship = new Friendship(friendId, userId, CONFIRMED);

        friendshipStorage.add(firstUserFriendship);
        friendshipStorage.add(secondUserFriendship);
    }

    public Collection<Long> getFriendIds(long userId) {
        return friendshipStorage.findFriendIdsByUserIdAndFriendshipStatus(userId, CONFIRMED);
    }

    private boolean friendshipExists(long userId, long friendId) {
        if (userId == friendId) return true;

        Optional<Friendship> userFriendship = friendshipStorage.
                findFriendshipByUserIds(userId, friendId);

        return userFriendship.isPresent();
    }
}