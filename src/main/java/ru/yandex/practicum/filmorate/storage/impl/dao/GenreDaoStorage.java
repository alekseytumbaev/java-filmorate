package ru.yandex.practicum.filmorate.storage.impl.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
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

    private final SelectAll<Genre> genreSelectAll;
    private final SelectById<Genre> genreSelectById;



    public GenreDaoStorage(JdbcTemplate jdbcTemplate, GenreMapper genreMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreMapper = genreMapper;

        String tableName = "genres";
        String idColumnName = "genre_id";
        genreSelectAll = new SelectAll<>(jdbcTemplate, genreMapper, tableName);
        genreSelectById = new SelectById<>(jdbcTemplate, genreMapper, tableName, idColumnName);
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
