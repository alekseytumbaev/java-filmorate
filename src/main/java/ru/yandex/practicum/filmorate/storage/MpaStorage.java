package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.film.MotionPictureAssociation;

import java.util.Collection;
import java.util.Optional;

public interface MpaStorage {

    Collection<MotionPictureAssociation> getAll();

    Optional<MotionPictureAssociation> getById(long id);
}