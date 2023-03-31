package ru.yandex.practicum.filmorate.storage.impl.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.GenreFilm;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.TableFieldsToEntityValuesMapper;
import ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries.Insert;

import java.sql.Date;
import java.util.*;

@Component
@Qualifier("filmDaoStorage")
public class FilmDaoStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FilmMapper filmMapper;
    private final GenreStorage genreStorage;

    private final Insert<Film> filmInsert;
    private final Insert<GenreFilm> genreFilmInsert;


    public FilmDaoStorage(JdbcTemplate jdbcTemplate, FilmMapper filmMapper, GenreStorage genreStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmMapper = filmMapper;
        this.genreStorage = genreStorage;

        String filmsTableName = "films";
        String filmsIdColumnName = "film_id";
        filmInsert = new Insert<>(jdbcTemplate, filmMapper,
                filmsTableName, filmsIdColumnName);

        TableFieldsToEntityValuesMapper<GenreFilm> genreFilmTfevMapper = genreFilm -> {
            Map<String, Object> rowAsMap = new HashMap<>();
            rowAsMap.put("genre_film_id", genreFilm.getId());
            rowAsMap.put("film_id", genreFilm.getFilm_id());
            rowAsMap.put("genre_id", genreFilm.getGenre_id());
            return rowAsMap;
        };
        genreFilmInsert = new Insert<>(jdbcTemplate, genreFilmTfevMapper,
                "genre_films", "genre_film_id");
    }

    @Override
    public Film add(Film entity) {
        Film film = filmInsert.execute(entity);

        addFilmGenres(film);
        return film;
    }

    @Override
    public Optional<Film> getById(long id) {
        String sql =
                "SELECT f.film_id," +
                        "f.film_name," +
                        "f.mpa_id," +
                        "mpa.mpa_name," +
                        "f.description," +
                        "f.release_date," +
                        "f.duration " +
                "FROM films AS f " +
                "INNER JOIN motion_picture_association AS mpa " +
                "ON f.mpa_id = mpa.mpa_id AND f.film_id = ?";

        List<Film> films = jdbcTemplate.query(sql, filmMapper, id);
        if (films.isEmpty()) return Optional.empty();

        Film film = films.get(0);
        Set<Genre> genres = new HashSet<>(genreStorage.getGenresByFilmId(id));
        film.setGenres(genres);

        return Optional.of(film);
    }

    @Override
    public Collection<Film> getAll() {
        String sql =
                "SELECT f.film_id," +
                        "f.film_name," +
                        "f.mpa_id," +
                        "mpa.mpa_name," +
                        "f.description," +
                        "f.release_date," +
                        "f.duration " +
                        "FROM films AS f " +
                        "INNER JOIN motion_picture_association AS mpa " +
                        "ON f.mpa_id = mpa.mpa_id";

        List<Film> films = jdbcTemplate.query(sql, filmMapper);

        for (Film film : films) {
            long filmId = film.getId();
            Set<Genre> genres = new HashSet<>(genreStorage.getGenresByFilmId(filmId));
            film.setGenres(genres);
        }
        return films;
    }

    @Override
    public Film update(Film film) throws FilmNotFoundException {
        String sql =
                "UPDATE films " +
                "SET film_name = ?, " +
                    "mpa_id = ?, " +
                    "description = ?, " +
                    "release_date = ?, " +
                    "duration = ? " +
                "WHERE film_id = ?";

        int numberOfUpdatedRows = jdbcTemplate.update(
                sql,
                film.getName(),
                film.getMpa().getId(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getId());

        if (numberOfUpdatedRows < 1)
            throw new FilmNotFoundException(
                    String.format("Film with id=%d not updated, because not found", film.getId()), film.getId());

        deleteFilmGenres(film);
        addFilmGenres(film);
        return film;
    }

    private void addFilmGenres(Film film) {
        long filmId = film.getId();
        for (Genre genre : film.getGenres()) {
            GenreFilm genreFilm = new GenreFilm(filmId, genre.getId());
            genreFilmInsert.execute(genreFilm);
        }
    }

    private void deleteFilmGenres(Film film) {
        String sql = "DELETE FROM genre_films WHERE genre_film_id=?";
        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update(sql, genre.getId());
        }
    }

    @Override
    public Collection<Film> getOrderedByLikesDesc(int amount) {
        String sql =
                "SELECT f.film_id," +
                        "f.film_name," +
                        "f.mpa_id," +
                        "mpa.mpa_name," +
                        "f.description," +
                        "f.release_date," +
                        "f.duration " +
                "FROM films AS f " +
                "INNER JOIN motion_picture_association AS mpa " +
                "ON f.mpa_id = mpa.mpa_id " +
                "WHERE f.film_id IN " +
                "(SELECT film_id " +
                "FROM likes " +
                "GROUP BY film_id " +
                "ORDER BY COUNT(film_id) DESC)";

        List<Film> films = jdbcTemplate.query(sql, filmMapper);

        for (Film film : films) {
            long filmId = film.getId();
            Set<Genre> genres = new HashSet<>(genreStorage.getGenresByFilmId(filmId));
            film.setGenres(genres);
        }
        return films;
    }
}