package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.film.MotionPictureAssociation;
import ru.yandex.practicum.filmorate.storage.impl.dao.MpaDaoStorage;

import java.util.Collection;
import java.util.Optional;


@Service
public class MpaService {

    private final MpaDaoStorage mpaDaoStorage;

    public MpaService(MpaDaoStorage mpaDaoStorage) {
        this.mpaDaoStorage = mpaDaoStorage;
    }

    public Collection<MotionPictureAssociation> getAll() {
        return mpaDaoStorage.getAll();
    }

    public MotionPictureAssociation getById(long id) throws MpaNotFoundException {
        Optional<MotionPictureAssociation> mpaOpt = mpaDaoStorage.getById(id);

        return mpaOpt.orElseThrow(() -> new MpaNotFoundException(
                String.format("MPA with id=%d not found", id), id));
    }
}
