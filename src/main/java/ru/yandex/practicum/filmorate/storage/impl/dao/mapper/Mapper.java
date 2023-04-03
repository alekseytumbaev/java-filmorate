package ru.yandex.practicum.filmorate.storage.impl.dao.mapper;

import org.springframework.jdbc.core.RowMapper;

public interface Mapper<T> extends RowMapper<T>, TableFieldsToEntityValuesMapper<T> {

}
