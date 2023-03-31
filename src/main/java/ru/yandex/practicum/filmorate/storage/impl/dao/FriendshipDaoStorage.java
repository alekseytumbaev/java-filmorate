package ru.yandex.practicum.filmorate.storage.impl.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.EFriendshipStatus;
import ru.yandex.practicum.filmorate.model.user.Friendship;
import ru.yandex.practicum.filmorate.model.user.FriendshipStatus;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.FriendshipMapper;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.UserMapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FriendshipDaoStorage implements FriendshipStorage {

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;
    private final FriendshipMapper friendshipMapper;

    public FriendshipDaoStorage(JdbcTemplate jdbcTemplate, UserMapper userMapper, FriendshipMapper friendshipMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
        this.friendshipMapper = friendshipMapper;
    }

    @Override
    public Friendship add(Friendship friendship) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("friendship").usingGeneratedKeyColumns("friendship_id");

        FriendshipStatus friendshipStatus = getFriendshipStatusByStatus(friendship.getStatus());
        friendship.setFriendshipStatus(friendshipStatus);

        Map<String, Object> rowAsMap = friendshipMapper.mapFieldsToValues(friendship);
        long id = simpleJdbcInsert.executeAndReturnKey(rowAsMap).longValue();

        friendship.setId(id);
        return friendship;
    }

    private FriendshipStatus getFriendshipStatusByStatus(EFriendshipStatus status) {
        String sql = "SELECT * FROM friendship_statuses WHERE status = ?";

        RowMapper<FriendshipStatus> statusRowMapper = (rs, rn) -> {
            long id = rs.getLong("friendship_status_id");
            String dbStatus = rs.getString("status");
            return new FriendshipStatus(id, EFriendshipStatus.valueOf(dbStatus));
        };

        return jdbcTemplate.query(sql, statusRowMapper, status.toString()).get(0);
    }

    @Override
    public Collection<User> getFriendsByUserIdAndFriendshipStatus(long userId, EFriendshipStatus friendshipStatus) {
        String sql =
                "SELECT * FROM users WHERE user_id IN " +
                "(SELECT f.friend_id FROM friendship AS f " +
                "INNER JOIN friendship_statuses AS fs ON f.status_id = fs.friendship_status_id " +
                "AND f.user_id = ? AND fs.status = ?)";

        return jdbcTemplate.query(sql, userMapper, userId, EFriendshipStatus.CONFIRMED.toString());
    }

    @Override
    public Collection<User> getMutualFriends(long firstUserId, long secondUserId) {
        String sql =
                "SELECT * FROM users WHERE user_id IN " +
                "(SELECT friend_id FROM friendship WHERE user_id = ? " +
                "INTERSECT " +
                "SELECT friend_id FROM friendship WHERE user_id = ?)";

        return jdbcTemplate.query(sql, userMapper, firstUserId, secondUserId);
    }

    @Override
    public Optional<Friendship> getFriendshipByUserIds(long userId, long friendId) {
        String sql =
                "SELECT f.friendship_id, f.user_id, f.friend_id, fs.friendship_status_id, fs.status " +
                "FROM friendship AS f " +
                "INNER JOIN friendship_statuses AS fs ON f.status_id = fs.friendship_status_id " +
                "WHERE user_id = ? AND friend_id = ?";
        List<Friendship> friendship = jdbcTemplate.query(sql, friendshipMapper, userId, friendId);
        return friendship.isEmpty() ? Optional.empty() : Optional.of(friendship.get(0));
    }

    @Override
    public void deleteFriendshipByUserIds(long userId, long friendId) {
        String sql = "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }
}
