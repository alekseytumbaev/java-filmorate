package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.DateIsNotBefore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class Film {

    private long id;

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "^.{0,200}$") //максимальная длина описания - 200 символов
    private String description;

    @NotNull
    @DateIsNotBefore("1895-12-28")
    private LocalDate releaseDate;

    @Positive
    private int duration; // в секундах
}
