package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private long nextId;
    private final Map<Long, Film> films;

    public FilmController() {
        this.films = new HashMap<>();
    }

    @PostMapping
    public ResponseEntity<Film> add(@RequestBody Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
        return ResponseEntity.ok(film);
    }

    @PutMapping
    public ResponseEntity<Film> update(@RequestBody Film film) {
        films.put(film.getId(), film);
        return ResponseEntity.ok(film);
    }

    @GetMapping
    public ResponseEntity<Collection<Film>> getAll() {
        return ResponseEntity.ok(films.values());
    }

    private long getNextId() {
        nextId++;
        return nextId;
    }
}
