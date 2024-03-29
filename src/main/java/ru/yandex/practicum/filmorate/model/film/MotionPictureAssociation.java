package ru.yandex.practicum.filmorate.model.film;

import lombok.*;
import ru.yandex.practicum.filmorate.model.Entity;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class MotionPictureAssociation extends Entity {

    @NotBlank
    private String  name;

    public MotionPictureAssociation() {
    }

    public MotionPictureAssociation(long id) {
        super(id);
    }

    public MotionPictureAssociation(long id, String name) {
        super(id);
        this.name = name;
    }
}
