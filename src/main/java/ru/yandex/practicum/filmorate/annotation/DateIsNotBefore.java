package ru.yandex.practicum.filmorate.annotation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Проверяет, что дата не раньше указанной. Формат даты - yyyy-MM-dd.
 * <p>
 * null-элементы считаются валидными.
 * <p>
 * Кидает {@link IllegalArgumentException}, если строка, переданная аннотации, в неправильном формате.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateIsNotBefore.Validator.class)
@Documented
public @interface DateIsNotBefore {

    String message() default "{ru.yandex.practicum.filmorate.annotation.DateIsNotBefore.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value();

    class Validator implements ConstraintValidator<DateIsNotBefore, LocalDate> {
        private LocalDate date;

        @Override
        public void initialize(DateIsNotBefore constraintAnnotation) {
            try {
                date = LocalDate.parse(constraintAnnotation.value());
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(
                        String.format("\"%s\" doesn't match date format yyyy-MM-dd", e.getParsedString()));
            }
        }

        @Override
        public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
            if (value == null) return  true;
            return !value.isBefore(date);
        }
    }
}
