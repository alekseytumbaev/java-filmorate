package ru.yandex.practicum.filmorate.storage.impl.dao.mapper;

import java.util.Map;

public interface TableFieldsToEntityValuesMapper<T> {

    /**
     * @return Мапа с именами столбцов из таблицы базы данных в качестве ключей
     * и соответствующими значениями полей передаваемого entity в качестве значений.
     * <p>
     * Поле id игнорируется.
     */
    Map<String, Object> mapFieldsToValues(T entity);
}
