package ru.yandex.practicum.filmorate.model.film;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.annotation.DateIsNotBefore;
import ru.yandex.practicum.filmorate.model.Entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class Film extends Entity {

    @NotBlank
    private String name;

    @NotNull
    private MotionPictureAssociation mpa;

    @NotNull
    private Set<Genre> genres = new HashSet<>();

    /**
     * Максимальная длина описания - 200 символов
     */
    @NotBlank
    @Pattern(regexp = "^.{0,200}$")
    private String description;

    /**
     * Дата выхода не должна быть раньше 1895-12-28
     */
    @NotNull
    @DateIsNotBefore("1895-12-28")
    private LocalDate releaseDate;

    /**
     * Длительность фильма в секундах
     */
    @Positive
    private int duration;

    /**
     * Id пользователей, которые поставили лайк фильму
     */
    @NotNull
    private Set<Long> likes = new HashSet<>();

    public Film(long id, String name, MotionPictureAssociation mpa,
                String description, LocalDate releaseDate, int duration) {
        super(id);
        this.name = name;
        this.mpa = mpa;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
