package ru.yandex.practicum.filmorate.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.model.Entity;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class FriendshipStatus extends Entity {

    @NotNull
    private EFriendshipStatus status;

    public FriendshipStatus(long id, EFriendshipStatus status) {
        super(id);
        this.status = status;
    }

    public FriendshipStatus(EFriendshipStatus status) {
        setStatus(status);
    }
}