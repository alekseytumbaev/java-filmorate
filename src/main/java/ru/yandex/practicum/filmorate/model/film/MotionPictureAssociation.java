package ru.yandex.practicum.filmorate.model.film;

import lombok.*;
import ru.yandex.practicum.filmorate.model.Entity;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class MotionPictureAssociation extends Entity {

    @NotBlank
    private EMotionPictureAssociation name;
}
