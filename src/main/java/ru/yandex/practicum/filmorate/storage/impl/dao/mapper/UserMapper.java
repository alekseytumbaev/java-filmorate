package ru.yandex.practicum.filmorate.storage.impl.dao.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.User;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserMapper implements Mapper<User> {

    private static final String USER_ID = "user_id";
    private static final String EMAIL = "email";
    private static final String LOGIN = "login";
    private static final String NAME = "name";
    private static final String BIRTHDAY = "birthday";

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong(USER_ID);
        String email = rs.getString(EMAIL);
        String login = rs.getString(LOGIN);
        String name = rs.getString(NAME);
        LocalDate birthday = rs.getDate(BIRTHDAY).toLocalDate();

        return new User(
                id,
                email,
                login,
                name,
                birthday
        );
    }

    /**
     * @return Мапа с именами столбцов из таблицы users в качестве ключей
     * и соответствующими значениями полей передаваемого entity в качестве значений.
     * <p>
     * Поле id игнорируется.
     */
    @Override
    public Map<String, Object> databaseFieldsToEntityValues(User entity) {
        Map<String, Object> rowAsMap = new HashMap<>();

        rowAsMap.put(EMAIL, entity.getEmail());
        rowAsMap.put(LOGIN, entity.getLogin());
        rowAsMap.put(NAME, entity.getName());
        rowAsMap.put(BIRTHDAY, Date.valueOf(entity.getBirthday()));

        return rowAsMap;
    }
}
