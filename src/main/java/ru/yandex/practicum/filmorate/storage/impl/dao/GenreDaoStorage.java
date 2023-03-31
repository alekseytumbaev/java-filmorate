package ru.yandex.practicum.filmorate.storage.impl.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.GenreMapper;

import java.util.Collection;

@Component
public class GenreDaoStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;
    private final GenreMapper genreMapper;

    public GenreDaoStorage(JdbcTemplate jdbcTemplate, GenreMapper genreMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreMapper = genreMapper;
    }

    @Override
    public Collection<Genre> getGenresByFilmId(long filmId) {
        String sql =
                "SELECT g.* " +
                "FROM genres AS g " +
                "INNER JOIN genre_films AS gf ON g.genre_id = gf.genre_film_id " +
                "AND gf.film_id = ?";
        return jdbcTemplate.query(sql, genreMapper, filmId);
    }
}
