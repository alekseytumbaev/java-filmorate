package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;
    private final MessageSource messageSource;

    @GetMapping("/popular")
    public Collection<Film> getTopMostPopular(@RequestParam Optional<Integer> count) {
        return filmService.getTopMostPopular(count.orElse(-1));
    }

    @PutMapping("/{id}/like/{userId}")
    public void like(@PathVariable @Positive long id, @PathVariable @Positive long userId) {
        filmService.like(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable @Positive long id, @PathVariable @Positive long userId) {
        filmService.removeLike(id, userId);
    }
    @PostMapping
    public ResponseEntity<Film> add(@RequestBody @Valid Film film) {
        Film addedFilm = filmService.add(film);
        log.info(messageSource.getMessage(
                "film_create",
                new Long[] {addedFilm.getId()},
                Locale.getDefault())
        );
        return ResponseEntity.ok(addedFilm);
    }

    @PutMapping
    public ResponseEntity<Film> update(@RequestBody @Valid Film film) {
        Film updatedFilm = filmService.update(film);
        log.info(messageSource.getMessage(
                "film_update",
                new Long[] {updatedFilm.getId()},
                Locale.getDefault()));
        return ResponseEntity.ok(updatedFilm);
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable @Positive long id) {
        return filmService.getByIdIfExists(id);
    }

    @GetMapping
    public ResponseEntity<Collection<Film>> getAll() {
        return ResponseEntity.ok(filmService.getAll());
    }
}
