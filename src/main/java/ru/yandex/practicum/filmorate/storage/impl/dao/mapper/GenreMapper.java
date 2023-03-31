package ru.yandex.practicum.filmorate.storage.impl.dao.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class GenreMapper implements Mapper<Genre> {
    private static final String GENRE_ID = "genre_id";
    private static final String GENRE_NAME = "genre_name";

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong(GENRE_ID);
        String name = rs.getString(GENRE_NAME);

        return new Genre(id, name);
    }

    @Override
    public Map<String, Object> mapFieldsToValues(Genre entity) {
        Map<String, Object> rowAsMap = new HashMap<>();

        rowAsMap.put(GENRE_ID, entity.getId());
        rowAsMap.put(GENRE_NAME, entity.getName());

        return rowAsMap;
    }
}
