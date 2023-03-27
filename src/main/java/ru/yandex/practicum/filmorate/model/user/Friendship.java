package ru.yandex.practicum.filmorate.model.user;

import lombok.*;
import ru.yandex.practicum.filmorate.model.Entity;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class Friendship extends Entity {

    private long userId;
    private long friendId;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @NotNull
    private FriendshipStatus friendshipStatus;

    public Friendship(long userId, long friendId, EFriendshipStatus status) {
        setUserId(userId);
        setFriendId(friendId);
        friendshipStatus = new FriendshipStatus(status);
    }

    public EFriendshipStatus getStatus() {
        return friendshipStatus.getStatus();
    }
}
