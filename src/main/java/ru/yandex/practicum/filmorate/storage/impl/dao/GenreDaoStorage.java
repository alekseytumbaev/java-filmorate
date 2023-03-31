package ru.yandex.practicum.filmorate.storage.impl.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries.SelectAll;
import ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries.SelectById;

import java.util.Collection;
import java.util.Optional;

@Component
public class GenreDaoStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;
    private final GenreMapper genreMapper;
    private final FilmDaoStorage filmDaoStorage;

    private final SelectAll<Genre> genreSelectAll;
    private final SelectById<Genre> genreSelectById;



    public GenreDaoStorage(JdbcTemplate jdbcTemplate, GenreMapper genreMapper, FilmDaoStorage filmDaoStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreMapper = genreMapper;
        this.filmDaoStorage = filmDaoStorage;

        String tableName = "genres";
        String idColumnName = "genre_id";
        genreSelectAll = new SelectAll<>(jdbcTemplate, genreMapper, tableName);
        genreSelectById = new SelectById<>(jdbcTemplate, genreMapper, tableName, idColumnName);
    }

    @Override
    public Collection<Genre> getGenresByFilmId(long filmId) {
        if (!filmDaoStorage.existsById(filmId))
            throw new FilmNotFoundException(
                    String.format("Cannot get genres of film with id=%d, because film not found", filmId), filmId);
        String sql =
                "SELECT g.* " +
                "FROM genres AS g " +
                "INNER JOIN genre_films AS gf ON g.genre_id = gf.genre_film_id " +
                "AND gf.film_id = ?";
        return jdbcTemplate.query(sql, genreMapper, filmId);
    }

    @Override
    public Collection<Genre> getAll() {
        return genreSelectAll.execute();
    }

    @Override
    public Optional<Genre> getById(long id) {
        return genreSelectById.execute(id);
    }
}
