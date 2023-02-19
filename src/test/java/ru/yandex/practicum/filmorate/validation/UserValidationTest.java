package ru.yandex.practicum.filmorate.validation;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.constraints.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidationTest extends AbstractValidationTest {
    private User user;

    @BeforeEach
    void beforeEach() {
        user = new User();
    }

    //////////////////////////////////////email/////////////////////////////////////////////////////////////////////////
    @Test
    void emailNotBlank() {
        String property = "email";

        user.setEmail(null);
        assertTrue(
                hasAnnotationViolation(user, property, NotBlank.class, validator),
                String.format("%s не должно равняться %s", property, user.getEmail())
        );

        user.setEmail("");
        assertTrue(
                hasAnnotationViolation(user, property, NotBlank.class, validator),
                String.format("%s не должно равняться %s", property, user.getEmail())
        );
    }

    @Test
    void emailShouldContainAt() {
        String property = "email";

        user.setEmail("no_at.mail.ru");
        assertTrue(
                hasAnnotationViolation(user, property, Email.class, validator),
                String.format("%s = %s не содержит @, но проходит валидацию", property, user.getEmail())
        );

        user.setEmail("with_at@mail.ru");
        assertFalse(
                hasAnnotationViolation(user, property, Email.class, validator),
                String.format("%s = %s валидно, но не проходит валидацию", property, user.getEmail())
        );
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////login/////////////////////////////////////////////////////////////////////////
    @Test
    void loginNotBlank() {
        String property = "login";

        user.setLogin(null);
        assertTrue(
                hasAnnotationViolation(user, property, NotBlank.class, validator),
                String.format("%s не должно равняться %s", property, user.getLogin())
        );

        user.setLogin("");
        assertTrue(
                hasAnnotationViolation(user, property, NotBlank.class, validator),
                String.format("%s не должно равняться %s", property, user.getLogin())
        );
    }

    @Test
    void loginShouldNotContainSpaces() {
        String property = "login";
        user.setLogin("login with spaces");
        assertTrue(
                hasAnnotationViolation(user, property, Pattern.class, validator),
                String.format("%s = %s содержит пробелы, но проходит валидацию", property, user.getLogin())
        );

        user.setLogin("loginWithoutSpaces");
        assertFalse(
                hasAnnotationViolation(user, property, Pattern.class, validator),
                String.format("%s = %s не содержит пробелов, но не проходит проверку", property, user.getLogin())
        );
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////name//////////////////////////////////////////////////////////////////////////
    @Test
    void nameNotNull() {
        String property = "name";

        user.setName(null);
        assertTrue(
                hasAnnotationViolation(user, property, NotNull.class, validator),
                String.format("%s не должно равняться %s", property, user.getName())
        );

        user.setName("Aleksey Tumbaev");
        assertFalse(
                hasAnnotationViolation(user, property, NotNull.class, validator),
                String.format("%s = %s, не равно null, но не проходит валидацию", property, user.getName())
        );
    }

    @Test
    void nameCanBeEmpty() {
        String property = "name";

        user.setName("");
        assertFalse(
                hasAnnotationViolation(user, property, validator),
                String.format("%s = \"%s\", может быть пустое, но не прошло валидацию", property, user.getName())
        );
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////birthday//////////////////////////////////////////////////////////////////////
    @Test
    void birthdayNotNull() {
        String property = "birthday";

        user.setBirthday(null);
        assertTrue(
                hasAnnotationViolation(user, property, NotNull.class, validator),
                String.format("%s не должно равняться %s", property, user.getBirthday())
        );

        user.setBirthday(LocalDate.now());
        assertFalse(
                hasAnnotationViolation(user, property, NotNull.class, validator),
                String.format("%s = %s, не равно null, но не прошло валидацию", property, user.getBirthday())
        );
    }

    @Test
    void birthdayPastOrPresent() {
        String property = "birthday";

        LocalDate past = LocalDate.now().minusDays(1);
        user.setBirthday(past);
        assertFalse(
                hasAnnotationViolation(user, property, validator),
                String.format("%s = %s, может быть в прошлом, но не прошло валидацию", property, user.getBirthday())
        );

        LocalDate present = LocalDate.now();
        user.setBirthday(present);
        assertFalse(
                hasAnnotationViolation(user, property, validator),
                String.format("%s = %s, может быть в настоящем, но не прошло валидацию", property, user.getBirthday())
        );

        LocalDate future = LocalDate.now().plusDays(1);
        user.setBirthday(future);
        assertTrue(
                hasAnnotationViolation(user, property, PastOrPresent.class, validator),
                String.format("%s = %s, не может быть в будущем, но прошло валидацию", property, user.getBirthday())
        );
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
