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

    private long film_id;
    private long genre_id;

    public GenreFilm() {
    }

    public GenreFilm(long film_id, long genre_id) {
        this.film_id = film_id;
        this.genre_id = genre_id;
    }
}
