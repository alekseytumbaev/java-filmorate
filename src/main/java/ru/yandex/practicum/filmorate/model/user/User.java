package ru.yandex.practicum.filmorate.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.model.Entity;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
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
    private Set<Long> friendshipIds = new HashSet<>();
}
