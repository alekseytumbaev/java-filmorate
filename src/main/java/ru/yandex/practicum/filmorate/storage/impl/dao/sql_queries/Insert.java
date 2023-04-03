package ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.yandex.practicum.filmorate.model.Entity;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.TableFieldsToEntityValuesMapper;

public class Insert<T extends Entity> extends SqlQuery {

    protected final String idColumnName;
    private SimpleJdbcInsert simpleJdbcInsert;
    private final TableFieldsToEntityValuesMapper<T> tfevMapper;

    public Insert(JdbcTemplate jdbcTemplate, TableFieldsToEntityValuesMapper<T> tfevMapper,
                  String tableName, String idColumnName) {
        super(jdbcTemplate, tableName);
        this.tfevMapper = tfevMapper;
        this.idColumnName = idColumnName;
    }

    public T execute(T entity) {
        SimpleJdbcInsert simpleJdbcInsert = getSimpleJdbcInsert(jdbcTemplate);
        long id = simpleJdbcInsert.executeAndReturnKey(tfevMapper.mapFieldsToValues(entity)).longValue();
        entity.setId(id);
        return entity;
    }

    protected SimpleJdbcInsert getSimpleJdbcInsert(JdbcTemplate jdbcTemplate) {
        if (simpleJdbcInsert == null) {
            simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName(tableName)
                    .usingGeneratedKeyColumns(idColumnName);
        }
        return simpleJdbcInsert;
    }
}
