package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class User {

    private long id;

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
