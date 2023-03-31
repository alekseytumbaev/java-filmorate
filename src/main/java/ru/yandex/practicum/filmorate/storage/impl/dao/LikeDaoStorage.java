package ru.yandex.practicum.filmorate.storage.impl.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.LikeStorage;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.LikeMapper;
import ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries.Insert;

@Component
public class LikeDaoStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;
    private final LikeMapper likeMapper;

    private final Insert<Like> likeInsert;

    public LikeDaoStorage(JdbcTemplate jdbcTemplate, LikeMapper likeMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.likeMapper = likeMapper;

        this.likeInsert = new Insert<>(jdbcTemplate, likeMapper,
                "likes", "like_id");
    }

    @Override
    public void addLike(long userId, long filmId) {
        likeInsert.execute(new Like(filmId, userId));
    }

    @Override
    public void removeLike(long userId, long filmId) {
        String sql = "DELETE FROM likes WHERE user_id = ? AND film_id = ?";
        jdbcTemplate.update(sql, userId, filmId);
    }
}
