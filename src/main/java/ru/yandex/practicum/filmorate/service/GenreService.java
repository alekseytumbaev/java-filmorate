package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.storage.impl.dao.GenreDaoStorage;

import java.util.Collection;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreDaoStorage genreDaoStorage;

    public GenreService(GenreDaoStorage genreDaoStorage) {
        this.genreDaoStorage = genreDaoStorage;
    }

    public Collection<Genre> getAll() {
        return genreDaoStorage.getAll();
    }

    public Genre getById(long id) throws GenreNotFoundException {
        Optional<Genre> genreOpt = genreDaoStorage.getById(id);

        return genreOpt.orElseThrow(() -> new GenreNotFoundException(
                String.format("Genre with id=%d not found", id), id));
    }
}
