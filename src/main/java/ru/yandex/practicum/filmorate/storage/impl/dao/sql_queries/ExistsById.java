package ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Entity;

import java.util.List;

public class ExistsById<T extends Entity> extends SqlQuery {

    private final String idColumnName;

    public ExistsById(JdbcTemplate jdbcTemplate, String tableName, String idColumnName) {
        super(jdbcTemplate, tableName);
        this.idColumnName = idColumnName;
    }

    public boolean execute(long id) {
        String sql = String.format("SELECT %1$s FROM %2$s WHERE %1$s = ?", idColumnName, tableName);
        List<Long> ids = jdbcTemplate.query(
                sql, (resultSet, rowNum) -> resultSet.getLong(idColumnName), id);
        return !ids.isEmpty();
    }
}
