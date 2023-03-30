package ru.yandex.practicum.filmorate.storage.impl.dao.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.EFriendshipStatus;
import ru.yandex.practicum.filmorate.model.user.Friendship;
import ru.yandex.practicum.filmorate.model.user.FriendshipStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class FriendshipMapper implements Mapper<Friendship> {

    private static final String FRIENDSHIP_ID = "friendship_id";
    private static final String USER_ID = "user_id";
    private static final String FRIEND_ID = "friend_id";
    private static final String STATUS_ID = "status_id";

    /**
     * @return Мапа с именами столбцов из таблицы users в качестве ключей
     * и соответствующими значениями полей передаваемого entity в качестве значений.
     * <p>
     * Поле id игнорируется.
     */
    @Override
    public Map<String, Object> mapFieldsToValues(Friendship entity) {
        Map<String, Object> rowAsMap = new HashMap<>();

        rowAsMap.put(USER_ID, entity.getUserId());
        rowAsMap.put(FRIEND_ID, entity.getFriendId());
        rowAsMap.put(STATUS_ID, entity.getFriendshipStatus().getId());

        return rowAsMap;
    }

    @Override
    public Friendship mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong(FRIENDSHIP_ID);
        long user_id = rs.getLong(USER_ID);
        long friend_id = rs.getLong(FRIEND_ID);

        long friendship_status_id = rs.getLong("friendship_status_id");
        String status = rs.getString("status");

        return new Friendship(
                id,
                user_id,
                friend_id,
                new FriendshipStatus(friendship_status_id, EFriendshipStatus.valueOf(status))
        );
    }
}
