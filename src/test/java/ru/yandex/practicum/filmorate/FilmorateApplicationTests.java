package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.MotionPictureAssociation;
import ru.yandex.practicum.filmorate.model.user.Friendship;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.impl.dao.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.*;
import static ru.yandex.practicum.filmorate.model.user.EFriendshipStatus.CONFIRMED;
import static ru.yandex.practicum.filmorate.model.user.EFriendshipStatus.RECIEVED;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
    
    private final FilmDaoStorage filmStorage;
    private final FriendshipDaoStorage friendshipStorage;
    private final GenreDaoStorage genreStorage;
    private final LikeDaoStorage likeStorage;
    private final MpaDaoStorage mpaStorage;
    private final UserDaoStorage userStorage;

    @Test
    public void filmAndLikeStorage() {
        LocalDate now = LocalDate.now();
        Film film = new Film("Film name", new MotionPictureAssociation(1, "G"),
                "Film descr", now, 100);
        Film addedFilm = filmStorage.add(film);
        long filmId = addedFilm.getId();
        film.setId(filmId);

        assertEquals("Созданный и добавленный фильм не одинаковы", film, addedFilm);
        assertTrue("Фильм не создан", filmStorage.existsById(filmId));
        
        Optional<Film> retrievedFilmOpt = filmStorage.getById(filmId);
        assertTrue("Не удается получить фильм", retrievedFilmOpt.isPresent());
        
        film.setMpa(new MotionPictureAssociation(2, "PG"));
        Film updatedFilm = filmStorage.update(film);
        assertEquals("Фильм не обновлен", updatedFilm.getMpa().getId(), 2L);
        
        
        
        LocalDate birthday = LocalDate.of(2002, 7, 31);
        User user = userStorage.add(new User("email@gmail.com", "login", "Name", birthday));
        long userId = user.getId();
        User user1 = userStorage.add(new User("email@gmail.com", "login", "Name", birthday));
        long userId1 = user1.getId();
        
        likeStorage.addLike(userId, filmId);
        likeStorage.addLike(userId1, filmId);
        Film film1 = new Film("Film name 2", new MotionPictureAssociation(3, "PG-13"),
                "Film descr 2", now, 80);       
        film1.setId(filmStorage.add(film1).getId());

        List<Film> mostPopularFilms = new ArrayList<>(filmStorage.getOrderedByLikesAcs(2));
        assertEquals("Фильмы неправильно отсортированы по популярности", mostPopularFilms.get(0), film);
        
        
        likeStorage.removeLike(userId, filmId);
        likeStorage.removeLike(userId1, filmId);
        likeStorage.addLike(userId, film1.getId());
        likeStorage.addLike(userId1, film1.getId());

        List<Film> mostPopularFilms1 = new ArrayList<>(filmStorage.getOrderedByLikesAcs(2));
        assertEquals("Фильмы неправильно отсортированы по популярности", mostPopularFilms1.get(1), film1);
    }
    
    
    @Test
    public void userAndFriendshipStorage() {
        LocalDate birthday = LocalDate.of(2002, 7, 31);
        User user = new User("email@gmail.com", "login", "Name", birthday);
        User addedUser = userStorage.add(user);
        long userId = addedUser.getId();
        user.setId(userId);
        
        assertEquals("Созданный и добавленный пользователь не одинаковы", user, addedUser);
        assertTrue("Пользователь не создан", userStorage.existsById(userId));

        Optional<User> retrievedFilmOpt = userStorage.getById(userId);
        assertTrue("Не удается получить пользователя", retrievedFilmOpt.isPresent());

        user.setLogin("updated_login");
        User updatedUser = userStorage.update(user);
        assertEquals("Пользователь не обновлен", updatedUser.getLogin(), "updated_login");

        
        User friend = userStorage.add(new User("second@gmail.com", "login2", "User 2", birthday));
        long friendId = friend.getId();
        friendshipStorage.add(new Friendship(userId, friendId, CONFIRMED));
        friendshipStorage.add(new Friendship(friendId, userId, RECIEVED));
        assertTrue("Дружба не сохранена",
                friendshipStorage.getFriendshipByUserIds(userId, friendId).isPresent());
        assertEquals("Пользователь не добавлен в друзья",
                friendshipStorage.getFriendsByUserIdAndFriendshipStatus(userId, CONFIRMED), List.of(friend));

        User friend1 = userStorage.add(new User("third@gmail.com", "login3", "User 3", birthday));
        long friendId1 = friend1.getId();
        friendshipStorage.add(new Friendship(friendId1, friendId, CONFIRMED));
        friendshipStorage.add(new Friendship(friendId, friendId1, RECIEVED));
        assertEquals("Общий друг не найден",
                friendshipStorage.getMutualFriends(userId, friendId1), List.of(friend));

        friendshipStorage.deleteFriendshipByUserIds(userId, friendId);

        assertTrue("Друзья не были удалены",
                friendshipStorage.getFriendshipByUserIds(userId, friendId).isEmpty());
    }

    @Test
    public void genreStorage() {
        assertFalse("Не удалось получить все жанры из базы", genreStorage.getAll().isEmpty());
        assertTrue("Не удалось получить жанр с id=1", genreStorage.getById(1).isPresent());
    }

    @Test
    public void mpaStorage() {
        assertFalse("Не удалось получить все MPA из базы", mpaStorage.getAll().isEmpty());
        assertTrue("Не удалось получить MPA с id=1", mpaStorage.getById(1).isPresent());
    }

}
