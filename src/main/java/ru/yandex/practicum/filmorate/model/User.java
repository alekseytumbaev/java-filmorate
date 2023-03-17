package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends Entity {

    @NotBlank
    @Email
    private String email;

    /**
     * Логин не должен содержать пробелов
     */
    @NotBlank
    @Pattern(regexp = "^\\S+$")
    private String login;

    private String name;

    @NotNull
    @PastOrPresent
    private LocalDate birthday;

    @NotNull
    private Set<Long> friendIds = new LinkedHashSet<>();

    /**
     * Id пользователей, которые отправили данному пользователю заявки в друзья
     */
    @NotNull
    private Set<Long> receivedFriendRequestIds = new LinkedHashSet<>();

    /**
     * Id пользователей, которым данный пользователь отправил заявки в друзья
     */
    @NotNull
    private Set<Long> sentFriendRequestIds = new LinkedHashSet<>();
}
