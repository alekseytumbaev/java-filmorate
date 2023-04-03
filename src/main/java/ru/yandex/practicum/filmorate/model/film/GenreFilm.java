package ru.yandex.practicum.filmorate.model.film;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.model.Entity;

/**
 * Класс, отражающий расшивочную таблицу gere_films
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GenreFilm extends Entity {

    private long filmId;
    private long genreId;

    public GenreFilm() {
    }

    public GenreFilm(long filmId, long genreId) {
        this.filmId = filmId;
        this.genreId = genreId;
    }
}
