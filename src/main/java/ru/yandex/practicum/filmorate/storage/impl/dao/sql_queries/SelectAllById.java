package ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class SelectAllById<T extends Entity> extends SqlQuery {

    private final String idColumnName;
    private final RowMapper<T> rowMapper;

    protected SelectAllById(JdbcTemplate jdbcTemplate, RowMapper<T> rowMapper, String tableName, String idColumnName) {
        super(jdbcTemplate, tableName);
        this.idColumnName = idColumnName;
        this.rowMapper = rowMapper;
    }

    public Collection<T> execute(Collection<Long> ids) {
        if (ids.isEmpty()) return new ArrayList<>();

        StringBuilder sqlBuilder = new StringBuilder(
                String.format("SELECT * FROM %s WHERE", tableName));

        for (Iterator<Long> iterator = ids.iterator(); iterator.hasNext(); ) {
            Long id = iterator.next();
            sqlBuilder.append(
                    String.format(" %s = %d", idColumnName, id));

            if (iterator.hasNext())
                sqlBuilder.append(" AND");
        }

        return jdbcTemplate.query(sqlBuilder.toString(), rowMapper);
    }
}
