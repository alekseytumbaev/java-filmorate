package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private long nextId;
    private final Map<Long, Film> films;

    public FilmController() {
        this.films = new HashMap<>();
    }

    @PostMapping
    public ResponseEntity<Film> add(@RequestBody @Valid Film film) {
        save(film);
        log.info("Добавлен фильм с id = {}", film.getId());
        return ResponseEntity.ok(film);
    }

    @PutMapping
    public ResponseEntity<Film> update(@RequestBody @Valid Film film) {
        try {
            renew(film);
        } catch (FilmNotFoundException e) {
            return new ResponseEntity<>(film, NOT_FOUND);
        }
        log.info("Обновлен фильм с id = {}", film.getId());
        return ResponseEntity.ok(film);
    }

    @GetMapping
    public ResponseEntity<Collection<Film>> getAll() {
        return ResponseEntity.ok(films.values());
    }

    private void save(Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
    }

    private void renew(Film film) {
        if (!films.containsKey(film.getId()))
            throw new FilmNotFoundException();
        films.put(film.getId(), film);
    }

    private long getNextId() {
        nextId++;
        return nextId;
    }
}
