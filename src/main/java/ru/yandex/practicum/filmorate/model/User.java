package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {

    private long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\S+$") //логин не должен содержать пробелов
    private String login;

    @NotNull
    private String name;

    @NotNull
    @PastOrPresent
    private LocalDate birthday;
}
