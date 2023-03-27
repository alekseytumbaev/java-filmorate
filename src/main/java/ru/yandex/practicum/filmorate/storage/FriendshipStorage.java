package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.user.EFriendshipStatus;
import ru.yandex.practicum.filmorate.model.user.Friendship;

import java.util.Collection;
import java.util.Optional;

public interface FriendshipStorage extends Storage<Friendship> {

    Collection<Long> findFriendIdsByUserIdAndFriendshipStatus(long userId, EFriendshipStatus friendshipStatus);

    Optional<Friendship> findFriendshipByUserIds(long userId, long friendId);

    void deleteFriendshipByUserIds(long userId, long friendId);

}
