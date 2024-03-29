package ru.yandex.practicum.filmorate.storage.impl.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.UserMapper;
import ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries.ExistsById;
import ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries.SelectAll;
import ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries.SelectById;
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
    private final SelectById<User> userSelectById;
    private final SelectAll<User> userSelectAll;
    private final ExistsById<User> userExistsById;

    public UserDaoStorage(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;

        String tableName = "users";
        String idColumnName = "user_id";
        this.userInsert = new Insert<>(jdbcTemplate, userMapper, tableName, idColumnName);
        this.userSelectById = new SelectById<>(jdbcTemplate, userMapper, tableName, idColumnName);
        this.userSelectAll = new SelectAll<>(jdbcTemplate, userMapper, tableName);
        this.userExistsById = new ExistsById<>(jdbcTemplate, tableName, idColumnName);

    }

    @Override
    public User add(User entity) {
        return userInsert.execute(entity);
    }

    @Override
    public Optional<User> getById(long id) {
        return userSelectById.execute(id);
    }

    @Override
    public Collection<User> getAll() {
        return userSelectAll.execute();
    }

    @Override
    public User update(User user) throws UserNotFoundException {
        String sql =
                "UPDATE users " +
                "SET email = ?, " +
                    "login = ?, " +
                    "name = ?, " +
                    "birthday = ? " +
                "WHERE user_id = ?";

        int numberOfUpdatedRows = jdbcTemplate.update(
                sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                Date.valueOf(user.getBirthday()),
                user.getId());

        if (numberOfUpdatedRows < 1)
            throw new UserNotFoundException(
                    String.format("User with id=%d not updated, because not found", user.getId()), user.getId());

        return user;
    }

    @Override
    public boolean existsById(long id) {
        return userExistsById.execute(id);
    }
}
