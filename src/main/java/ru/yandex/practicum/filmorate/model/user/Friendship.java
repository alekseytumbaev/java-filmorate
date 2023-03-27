package ru.yandex.practicum.filmorate.model.user;

import lombok.*;
import ru.yandex.practicum.filmorate.model.Entity;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Friendship extends Entity {

    @NotNull
    private List<Long> userId;

    @NotNull
    private List<Long> friendId;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @NotNull
    private FriendshipStatus friendshipStatus;

    public EFriendshipStatus getStatus() {
        return friendshipStatus.getStatus();
    }

}
