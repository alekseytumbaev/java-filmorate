package ru.yandex.practicum.filmorate.storage.impl.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface Mapper<T extends Entity> extends RowMapper<T> {

    /**
     * @return Мапа с именами столбцов из таблицы базы данных в качестве ключей
     * и соответствующими значениями полей передаваемого entity в качестве значений.
     * <p>
     * Поле id игнорируется.
     */
    Map<String, Object> databaseFieldsToEntityValues(T entity);

    @Override
    T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
