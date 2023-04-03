package ru.yandex.practicum.filmorate.storage.impl.dao.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Like;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class LikeMapper implements Mapper<Like> {

    private static final String LIKE_ID = "like_id";
    private static final String FILM_ID = "film_id";
    private static final String USER_ID = "user_id";

    @Override
    public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong(LIKE_ID);
        long filmId = rs.getLong(FILM_ID);
        long userId = rs.getLong(USER_ID);

        return new Like(id, filmId, userId);
    }

    @Override
    public Map<String, Object> mapFieldsToValues(Like entity) {
        Map<String, Object> rowAsMap = new HashMap<>();

        rowAsMap.put(LIKE_ID, entity.getId());
        rowAsMap.put(FILM_ID, entity.getFilmId());
        rowAsMap.put(USER_ID, entity.getUserId());

        return rowAsMap;
    }
}
