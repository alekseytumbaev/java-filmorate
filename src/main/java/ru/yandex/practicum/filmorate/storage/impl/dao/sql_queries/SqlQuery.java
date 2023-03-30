package ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Entity;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.Mapper;

public abstract class SqlQuery<T extends Entity> {

    protected final JdbcTemplate jdbcTemplate;
    protected final Mapper<T> mapper;
    protected final String tableName;

    protected SqlQuery(JdbcTemplate jdbcTemplate, Mapper<T> mapper, String tableName) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
        this.tableName = tableName;
    }
}
