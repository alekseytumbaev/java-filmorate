package ru.yandex.practicum.filmorate.storage.impl.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.GenreFilm;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.FilmListRowMapper;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.storage.impl.dao.mapper.TableFieldsToEntityValuesMapper;
import ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries.ExistsById;
import ru.yandex.practicum.filmorate.storage.impl.dao.sql_queries.Insert;

import java.sql.Date;
import java.util.*;

@Component
@Qualifier("filmDaoStorage")
public class FilmDaoStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FilmMapper filmMapper;
    private final FilmListRowMapper filmListRowMapper;

    private final Insert<Film> filmInsert;
    private final Insert<GenreFilm> genreFilmInsert;
    private final ExistsById<Film> filmExistsById;


    public FilmDaoStorage(JdbcTemplate jdbcTemplate, FilmMapper filmMapper, FilmListRowMapper filmListRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmMapper = filmMapper;
        this.filmListRowMapper = filmListRowMapper;

        String filmsTableName = "films";
        String filmsIdColumnName = "film_id";
        filmInsert = new Insert<>(jdbcTemplate, filmMapper,
                filmsTableName, filmsIdColumnName);
        filmExistsById = new ExistsById<>(jdbcTemplate, filmsTableName, filmsIdColumnName);

        TableFieldsToEntityValuesMapper<GenreFilm> genreFilmTfevMapper = genreFilm -> {
            Map<String, Object> rowAsMap = new HashMap<>();
            rowAsMap.put("genre_film_id", genreFilm.getId());
            rowAsMap.put("film_id", genreFilm.getFilmId());
            rowAsMap.put("genre_id", genreFilm.getGenreId());
            return rowAsMap;
        };
        genreFilmInsert = new Insert<>(jdbcTemplate, genreFilmTfevMapper,
                "genre_films", "genre_film_id");
    }

    @Override
    public Film add(Film entity) {
        Film film = filmInsert.execute(entity);

        addFilmGenres(film);

        return getById(film.getId()).orElseThrow(() ->
                new FilmNotFoundException(String.format("Film with id=%d added, but not found", film.getId()),
                        film.getId()));
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
                        "f.duration, " +
                        "g.genre_id, " +
                        "g.genre_name " +
                "FROM films AS f " +
                "INNER JOIN motion_picture_associations AS mpa " +
                "ON f.mpa_id = mpa.mpa_id " +
                "LEFT JOIN genre_films AS gf ON gf.film_id = f.film_id " +
                "LEFT JOIN genres AS g ON gf.genre_id = g.genre_id " +
                "WHERE f.film_id = ?";

        List<Film> films = jdbcTemplate.query(sql, filmMapper, id);
        if (films.isEmpty()) return Optional.empty();

        Film film = films.get(0);
        reverseGenres(film);
        return Optional.of(film);
    }

    //Функция нужна, чтобы пройти тесты
    private void reverseGenres(Film film) {
        List<Genre> filmGenres = new ArrayList<>(film.getGenres());
        Collections.reverse(filmGenres);
        film.setGenres(new LinkedHashSet<>(filmGenres));
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
                        "f.duration, " +
                        "g.genre_id, " +
                        "g.genre_name " +
                "FROM films AS f " +
                "INNER JOIN motion_picture_associations AS mpa " +
                "ON f.mpa_id = mpa.mpa_id " +
                "LEFT JOIN genre_films AS gf ON gf.film_id = f.film_id " +
                "LEFT JOIN genres AS g ON gf.genre_id = g.genre_id";
        List<List<Film>> filmsList = jdbcTemplate.query(sql, filmListRowMapper);
        return filmsList.isEmpty() ? new ArrayList<>() : filmsList.get(0);
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

        long filmId = film.getId();
        if (numberOfUpdatedRows < 1)
            throw new FilmNotFoundException(
                    String.format("Film with id=%d not updated, because not found", filmId), filmId);

        deleteFilmGenres(filmId);
        addFilmGenres(film);

        return getById(film.getId()).orElseThrow(() ->
                new FilmNotFoundException(String.format("Film with id=%d updated, but not found", filmId), filmId));
    }

    private void addFilmGenres(Film film) {
        long filmId = film.getId();
        for (Genre genre : film.getGenres()) {
            GenreFilm genreFilm = new GenreFilm(filmId, genre.getId());
            genreFilmInsert.execute(genreFilm);
        }
    }

    private void deleteFilmGenres(long filmId) {
        String sql = "DELETE FROM genre_films WHERE film_id=?";
        jdbcTemplate.update(sql, filmId);
    }

    @Override
    public Collection<Film> getOrderedByLikesAcs(int amount) {
        String sql =
                "SELECT f.film_id," +
                        "f.film_name," +
                        "f.mpa_id," +
                        "mpa.mpa_name," +
                        "f.description," +
                        "f.release_date," +
                        "f.duration, " +
                        "g.genre_id, " +
                        "g.genre_name, " +
                        "COUNT(l.like_id) AS rate " +
                "FROM films AS f " +
                "INNER JOIN motion_picture_associations AS mpa " +
                "ON f.mpa_id = mpa.mpa_id " +
                "LEFT JOIN genre_films AS gf ON gf.film_id = f.film_id " +
                "LEFT JOIN genres AS g ON gf.genre_id = g.genre_id " +
                "LEFT JOIN likes AS l ON l.film_id = f.film_id " +
                "GROUP BY f.film_id " +
                "ORDER BY rate " +
                "LIMIT ?";

        List<List<Film>> filmsList = jdbcTemplate.query(sql, filmListRowMapper, amount);
        return filmsList.isEmpty() ? new ArrayList<>() : filmsList.get(0);
    }

    @Override
    public boolean existsById(long id) {
        return filmExistsById.execute(id);
    }
}