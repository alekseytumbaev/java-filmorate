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

    @NotBlank
    @Pattern(regexp = "^\\S+$") //логин не должен содержать пробелов
    private String login;

    private String name;

    @NotNull
    @PastOrPresent
    private LocalDate birthday;

    @NotNull
    private Set<Long> friendIds = new LinkedHashSet<>();
}
