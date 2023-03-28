package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.user.EFriendshipStatus;
import ru.yandex.practicum.filmorate.model.user.Friendship;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Collection;
import java.util.Optional;

public interface FriendshipStorage {

    Friendship add(Friendship friendship);

    Collection<User> getFriendsByUserIdAndFriendshipStatus(long userId, EFriendshipStatus friendshipStatus);

    Collection<User> getMutualFriends(long firstUserId, long secondUserId);

    Optional<Friendship> getFriendshipByUserIds(long userId, long friendId);

    void deleteFriendshipByUserIds(long userId, long friendId);

}
