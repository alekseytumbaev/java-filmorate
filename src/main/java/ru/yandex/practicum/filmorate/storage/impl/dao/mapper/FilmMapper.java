package ru.yandex.practicum.filmorate.storage.impl.dao.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.MotionPictureAssociation;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class FilmMapper implements Mapper<Film> {

    private static final String FILM_ID = "film_id";
    private static final String FILM_NAME = "film_name";
    private static final String MPA_ID = "mpa_id";
    private static final String DESCRIPTION = "description";
    private static final String RELEASE_DATE = "release_date";
    private static final String DURATION = "duration";

    @Override
    public Map<String, Object> mapFieldsToValues(Film entity) {
        Map<String, Object> rowAsMap = new HashMap<>();

        rowAsMap.put(FILM_ID, entity.getId());
        rowAsMap.put(FILM_NAME, entity.getName());
        rowAsMap.put(MPA_ID, entity.getMpa().getId());
        rowAsMap.put(DESCRIPTION, entity.getDescription());
        rowAsMap.put(RELEASE_DATE, Date.valueOf(entity.getReleaseDate()));
        rowAsMap.put(DURATION, entity.getDuration());

        return rowAsMap;
    }

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong(FILM_ID);
        String filmName = rs.getString(FILM_NAME);
        long mpaId = rs.getLong("mpa_id");
        String mpaName = rs.getString("mpa_name");
        String description = rs.getString(DESCRIPTION);
        LocalDate releaseDate = rs.getDate(RELEASE_DATE).toLocalDate();
        int duration = rs.getInt(DURATION);

        MotionPictureAssociation mpa = new MotionPictureAssociation(mpaId, mpaName);
        return new Film(id, filmName, mpa, description, releaseDate, duration);
    }
}
