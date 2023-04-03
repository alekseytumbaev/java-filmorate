package ru.yandex.practicum.filmorate.storage.impl.dao.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.MotionPictureAssociation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MpaMapper implements Mapper<MotionPictureAssociation> {
    private static final String MPA_ID = "mpa_id";
    private static final String MPA_NAME = "mpa_name";

    @Override
    public MotionPictureAssociation mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong(MPA_ID);
        String mpaName = rs.getString(MPA_NAME);
        return new MotionPictureAssociation(id, mpaName);
    }

    @Override
    public Map<String, Object> mapFieldsToValues(MotionPictureAssociation entity) {
        Map<String, Object> mapAsRow = new HashMap<>();

        mapAsRow.put(MPA_ID, entity.getId());
        mapAsRow.put(MPA_NAME, entity.getName());

        return mapAsRow;
    }
}
