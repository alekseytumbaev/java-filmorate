package ru.yandex.practicum.filmorate.storage.impl.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.UserMapper;

import java.sql.Date;

@Component
@Qualifier("userDaoStorage")
public class UserDaoStorage extends AbstractDaoStorage<User> implements UserStorage {

    public UserDaoStorage(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        super(jdbcTemplate, userMapper, "users", "user_id");
    }

    @Override
    public User update(User user) {
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
