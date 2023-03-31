package ru.yandex.practicum.filmorate.model.film;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.model.Entity;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class Genre extends Entity {
    @NotBlank
    private String name;

    public Genre(long id, String name) {
        super(id);
        this.name = name;
    }
}