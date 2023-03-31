package ru.yandex.practicum.filmorate.storage.impl.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.MotionPictureAssociation;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries.SelectAll;
import ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries.SelectById;

import java.util.Collection;
import java.util.Optional;

@Component
public class MpaDaoStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaMapper mpaMapper;

    private final SelectAll<MotionPictureAssociation> mpaSelectAll;
    private final SelectById<MotionPictureAssociation> mpaSelectById;

    public MpaDaoStorage(JdbcTemplate jdbcTemplate, MpaMapper mpaMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaMapper = mpaMapper;

        String tableName = "motion_picture_associations";
        String idColumnName = "mpa_id";
        mpaSelectAll = new SelectAll<>(jdbcTemplate, mpaMapper, tableName);
        mpaSelectById = new SelectById<>(jdbcTemplate, mpaMapper, tableName, idColumnName);
    }

    @Override
    public Collection<MotionPictureAssociation> getAll() {
        return mpaSelectAll.execute();
    }

    @Override
    public Optional<MotionPictureAssociation> getById(long id) {
        return mpaSelectById.execute(id);
    }
}
