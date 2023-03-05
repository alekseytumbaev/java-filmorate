package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping("/popular")
    public Collection<Film> getTopMostPopular(@RequestParam Optional<Integer> count) {
        return filmService.getTopMostPopular(count.orElse(-1));
    }

    @PutMapping("/{id}/like/{userId}")
    public void like(@PathVariable long id, @PathVariable long userId) {
        filmService.like(id, userId);
        log.info(
                String.format("User with id=%d liked film with id=%d", userId, id)
        );
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable long id, @PathVariable long userId) {
        filmService.removeLike(id, userId);
        log.info(
                String.format("User with id=%d removed like from film with id=%d", userId, id)
        );
    }

    @PostMapping
    public Film add(@RequestBody @Valid Film film) {
        Film addedFilm = filmService.add(film);
        log.info(
                String.format("Film with id=%d was added", addedFilm.getId())
        );
        return addedFilm;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        Film updatedFilm = filmService.update(film);
        log.info(
                String.format("Film with id=%d was updated", updatedFilm.getId())
        );
        return updatedFilm;
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable long id) {
        return filmService.getByIdIfExists(id);
    }

    @GetMapping
    public ResponseEntity<Collection<Film>> getAll() {
        return ResponseEntity.ok(filmService.getAll());
    }
}
