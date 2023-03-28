package ru.yandex.practicum.filmorate.storage.impl.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Entity;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.Mapper;

import java.util.*;

public abstract class AbstractDaoStorage<T extends Entity> implements Storage<T> {

    protected final JdbcTemplate jdbcTemplate;
    protected final Mapper<T> mapper;
    protected final String tableName;
    protected final String idColumnName;
    private SimpleJdbcInsert simpleJdbcInsert;

    protected AbstractDaoStorage(JdbcTemplate jdbcTemplate, Mapper<T> mapper, String tableName, String idColumnName) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
        this.tableName = tableName;
        this.idColumnName = idColumnName;
    }

    @Override
    public T add(T entity) {
        SimpleJdbcInsert simpleJdbcInsert = getSimpleJdbcInsert(jdbcTemplate);

        long id = simpleJdbcInsert.executeAndReturnKey(mapper.databaseFieldsToEntityValues(entity)).longValue();
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

    @Override
    public abstract T update(T entity) throws EntityNotFoundException;

    @Override
    public Optional<T> getById(long id) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", tableName, idColumnName);
        List<T> entities = jdbcTemplate.query(sql, mapper, id);

        if (entities.isEmpty())
            return Optional.empty();
        else
            return Optional.of(entities.get(0));
    }

    @Override
    public Collection<T> getAll() {
        String sql = String.format("SELECT * FROM %s", tableName);
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public Collection<T> getAllById(Collection<Long> ids) {
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

        return jdbcTemplate.query(sqlBuilder.toString(), mapper);
    }

    @Override
    public boolean existsById(long id) {
        String sql = String.format("SELECT %1$s FROM %2$s WHERE %1$s = ?", idColumnName, tableName);
        List<Long> ids = jdbcTemplate.query(
                sql, (resultSet, rowNum) -> resultSet.getLong(idColumnName), id);
        return !ids.isEmpty();
    }
}
