package ru.yandex.practicum.filmorate.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.model.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
public class FriendshipStatus extends Entity {
    private EFriendshipStatus status;
}
