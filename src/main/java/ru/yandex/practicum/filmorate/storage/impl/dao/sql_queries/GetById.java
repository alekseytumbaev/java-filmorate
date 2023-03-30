package ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Entity;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.Mapper;

import java.util.List;
import java.util.Optional;

public class GetById<T extends Entity> extends SqlQuery<T> {

    protected final String idColumnName;

    public GetById(JdbcTemplate jdbcTemplate, Mapper<T> mapper, String tableName, String idColumnName) {
        super(jdbcTemplate, mapper, tableName);
        this.idColumnName = idColumnName;
    }

    public Optional<T> execute(long id) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", tableName, idColumnName);
        List<T> entities = jdbcTemplate.query(sql, mapper, id);

        if (entities.isEmpty())
            return Optional.empty();
        else
            return Optional.of(entities.get(0));
    }
}
