package ru.yandex.practicum.filmorate.storage.impl.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FilmListRowMapper implements RowMapper<List<Film>> {

    private final FilmMapper filmMapper;

    public FilmListRowMapper(FilmMapper filmMapper) {
        this.filmMapper = filmMapper;
    }

    @Override
    public List<Film> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<Film> films = new ArrayList<>();
        while (!rs.isLast()) {
            Film film = filmMapper.mapRow(rs, rowNum);
            films.add(film);
        }
        films.add(filmMapper.mapRow(rs, rowNum));
        return films;
    }
}
