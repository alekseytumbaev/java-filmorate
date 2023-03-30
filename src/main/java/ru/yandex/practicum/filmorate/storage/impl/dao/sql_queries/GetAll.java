package ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collection;

public class GetAll<T> extends SqlQuery {

    private final RowMapper<T> rowMapper;

    public GetAll(JdbcTemplate jdbcTemplate, RowMapper<T> rowMapper, String tableName) {
        super(jdbcTemplate, tableName);
        this.rowMapper = rowMapper;
    }

    public Collection<T> execute() {
        String sql = String.format("SELECT * FROM %s", tableName);
        return jdbcTemplate.query(sql, rowMapper);
    }
}
