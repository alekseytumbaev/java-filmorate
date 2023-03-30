package ru.yandex.practicum.filmorate.storage.impl.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.UserMapper;
import ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries.GetAll;
import ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries.GetById;
import ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries.Insert;

import java.sql.Date;
import java.util.Collection;
import java.util.Optional;

@Component
@Qualifier("userDaoStorage")
public class UserDaoStorage  implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    private final Insert<User> userInsert;
    private final GetById<User> userGetById;
    private final GetAll<User> userGetAll;

    public UserDaoStorage(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;

        String tableName = "users";
        String idColumnName = "user_id";
        this.userInsert = new Insert<>(jdbcTemplate, userMapper, tableName, idColumnName);
        this.userGetById = new GetById<>(jdbcTemplate, userMapper, tableName, idColumnName);
        this.userGetAll = new GetAll<>(jdbcTemplate, userMapper, tableName);

    }

    @Override
    public User add(User entity) {
        return userInsert.execute(entity);
    }

    @Override
    public Optional<User> getById(long id) {
        return userGetById.execute(id);
    }

    @Override
    public Collection<User> getAll() {
        return userGetAll.execute();
    }

    @Override
    public User update(User user) throws UserNotFoundException {
        String sql = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ? WHERE user_id = " + user.getId();

        int numberOfUpdatedRows = jdbcTemplate.update(
                sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                Date.valueOf(user.getBirthday()));

        if (numberOfUpdatedRows < 1)
            throw new UserNotFoundException(
                    String.format("User with id=%d not updated, because not found", user.getId()), user.getId());

        return user;
    }
}
