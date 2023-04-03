package ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class SqlQuery {

    protected final JdbcTemplate jdbcTemplate;
    protected final String tableName;

    protected SqlQuery(JdbcTemplate jdbcTemplate, String tableName) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
    }
}
