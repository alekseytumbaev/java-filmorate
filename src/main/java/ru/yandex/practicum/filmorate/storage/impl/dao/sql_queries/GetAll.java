package ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Entity;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.Mapper;

import java.util.Collection;

public class GetAll<T extends Entity> extends SqlQuery<T> {
    public GetAll(JdbcTemplate jdbcTemplate, Mapper<T> mapper, String tableName) {
        super(jdbcTemplate, mapper, tableName);
    }

    public Collection<T> execute() {
        String sql = String.format("SELECT * FROM %s", tableName);
        return jdbcTemplate.query(sql, mapper);
    }
}
