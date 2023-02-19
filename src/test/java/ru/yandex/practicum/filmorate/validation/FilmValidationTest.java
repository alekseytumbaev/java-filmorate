package ru.yandex.practicum.filmorate.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.annotation.DateIsNotBefore;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmValidationTest extends AbstractValidationTest {
    static Film film;

    @BeforeEach
    void beforeEach() {
        film = new Film();
    }

    //////////////////////////////////////name//////////////////////////////////////////////////////////////////////////
    @Test
    void nameNotBlank() {
        String property = "name";

        film.setName(null);
        assertTrue(
                hasAnnotationViolation(film, property, NotBlank.class, validator),
                String.format("%s не должно равняться %s", property, film.getName())
        );

        film.setName("");
        assertTrue(
                hasAnnotationViolation(film, property, NotBlank.class, validator),
                String.format("%s не должно равняться %s", property, film.getName())
        );
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////description///////////////////////////////////////////////////////////////////
    @Test
    void descriptionNotBlank() {
        String property = "description";

        film.setDescription(null);
        assertTrue(
                hasAnnotationViolation(film, property, NotBlank.class, validator),
                String.format("%s не должно равняться %s", property, film.getDescription())
        );

        film.setDescription("");
        assertTrue(
                hasAnnotationViolation(film, property, NotBlank.class, validator),
                String.format("%s не должно равняться %s", property, film.getDescription())
        );
    }

    @Test
    void descriptionMaxLengthShouldBe200() {
        String property = "description";

        film.setDescription("S".repeat(201));
        assertTrue(
                hasAnnotationViolation(film, property, Pattern.class, validator),
                String.format("%s.length() = %s, не должно быть больше 200, но прошло валидацию",
                        property, film.getDescription().length())
        );

        film.setDescription("S".repeat(200));
        assertFalse(
                hasAnnotationViolation(film, property, Pattern.class, validator),
                String.format("%s.length() = %s, может быть 200, но не прошло валидацию",
                        property, film.getDescription().length())
        );

        film.setDescription("S".repeat(199));
        assertFalse(
                hasAnnotationViolation(film, property, Pattern.class, validator),
                String.format("%s = %s, валидно, но не прошло валидацию",
                        property, film.getDescription())
        );
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////releaseDate///////////////////////////////////////////////////////////////////
    @Test
    void releaseDateNotNull() {
        String property = "releaseDate";

        film.setReleaseDate(null);
        assertTrue(
                hasAnnotationViolation(film, property, NotNull.class, validator),
                String.format("%s не должно равняться %s", property, film.getReleaseDate())
        );

        film.setReleaseDate(LocalDate.now());
        assertFalse(
                hasAnnotationViolation(film, property, NotNull.class, validator),
                String.format("%s = %s, не равно null, но не проходит валидацию", property, film.getReleaseDate())
        );
    }

    @Test
    void releaseDateIsNotBefore1895_12_28() {
        String property = "releaseDate";

        film.setReleaseDate(LocalDate.parse("1895-12-27"));
        assertTrue(
                hasAnnotationViolation(film, property, DateIsNotBefore.class, validator),
                String.format("%s = %s, не должно быть раньше 1895-12-28, но прошло валидацию",
                        property, film.getReleaseDate())
        );

        film.setReleaseDate(LocalDate.parse("1895-12-28"));
        assertFalse(
                hasAnnotationViolation(film, property, DateIsNotBefore.class, validator),
                String.format("%s = %s, не должно быть раньше 1895-12-28, но не прошло валидацию",
                        property, film.getReleaseDate())
        );

        film.setReleaseDate(LocalDate.now());
        assertFalse(
                hasAnnotationViolation(film, property, DateIsNotBefore.class, validator),
                String.format("%s = %s, валидно, но не прошло валидацию",
                        property, film.getReleaseDate())
        );
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////releaseDate///////////////////////////////////////////////////////////////////
    @Test
    void durationPositive() {
        String property = "duration";

        film.setDuration(-1);
        assertTrue(
                hasAnnotationViolation(film, property, Positive.class, validator),
                String.format("%s = %s, должно быть положительно, но прошло валидацию",
                        property, film.getDuration())
        );

        film.setDuration(0);
        assertTrue(
                hasAnnotationViolation(film, property, Positive.class, validator),
                String.format("%s = %s, должно быть положительно, но прошло валидацию",
                        property, film.getDuration())
        );

        film.setDuration(1);
        assertFalse(
                hasAnnotationViolation(film, property, Positive.class, validator),
                String.format("%s = %s, валидно, но не прошло валидацию",
                        property, film.getDuration())
        );
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
