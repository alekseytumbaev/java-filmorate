package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.user.EFriendshipStatus;
import ru.yandex.practicum.filmorate.model.user.Friendship;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Collection;
import java.util.Optional;

public interface FriendshipStorage extends Storage<Friendship> {

    Collection<User> findFriendsByUserIdAndFriendshipStatus(long userId, EFriendshipStatus friendshipStatus);

    Collection<User> findMutualFriends(long firstUserId, long secondUserId);

    Optional<Friendship> findFriendshipByUserIds(long userId, long friendId);

    void deleteFriendshipByUserIds(long userId, long friendId);

}
