package ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Entity;

import java.util.List;
import java.util.Optional;

public class SelectById<T extends Entity> extends SqlQuery {

    protected final String idColumnName;
    private final RowMapper<T> rowMapper;

    public SelectById(JdbcTemplate jdbcTemplate, RowMapper<T> rowMapper, String tableName, String idColumnName) {
        super(jdbcTemplate, tableName);
        this.idColumnName = idColumnName;
        this.rowMapper = rowMapper;
    }

    public Optional<T> execute(long id) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", tableName, idColumnName);
        List<T> entities = jdbcTemplate.query(sql, rowMapper, id);

        if (entities.isEmpty())
            return Optional.empty();
        else
            return Optional.of(entities.get(0));
    }
}
