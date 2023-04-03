package ru.yandex.practicum.filmorate.model.user;

import lombok.*;
import ru.yandex.practicum.filmorate.model.Entity;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class Friendship extends Entity {

    private long userId;
    private long friendId;

    @NotNull
    private FriendshipStatus friendshipStatus;

    public Friendship() {
    }

    public Friendship(long id, long userId, long friendId, FriendshipStatus friendshipStatus) {
        super(id);
        this.userId = userId;
        this.friendId = friendId;
        this.friendshipStatus = friendshipStatus;
    }

    public Friendship(long userId, long friendId, EFriendshipStatus status) {
        setUserId(userId);
        setFriendId(friendId);
        friendshipStatus = new FriendshipStatus(status);
    }

    public EFriendshipStatus getStatus() {
        return friendshipStatus.getStatus();
    }
}