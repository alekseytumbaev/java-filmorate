package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.error.ErrorResponse;
import ru.yandex.practicum.filmorate.model.error.ValidationViolation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    private final MessageSource messageSource;

    @Autowired
    public ErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse onUserNotFoundException(final UserNotFoundException e) {
        String message = getErrorMessage(
                "ErrorResponse.message.userNotFound",
                new Object[]{e.getUserId()},
                e,
                String.format("User with id=%d not found", e.getUserId())
        );
        log.warn(message, e);
        return new ErrorResponse(message);
    }

    @ExceptionHandler(FilmNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse onFilmNotFoundException(final FilmNotFoundException e) {
        String message = getErrorMessage(
                "ErrorResponse.message.filmNotFound",
                new Object[]{e.getFilmId()},
                e,
                String.format("Film with id=%d not found", e.getFilmId())
        );
        log.warn(message, e);
        return new ErrorResponse(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse onMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        String message = getErrorMessage(
                "ErrorResponse.message.validation",
                null,
                e,
                "Validation failed"
        );
        List<ValidationViolation> violations = new LinkedList<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            ValidationViolation violation = new ValidationViolation(
                    fieldError.getField(), fieldError.getDefaultMessage());
            violations.add(violation);
        }
        ErrorResponse error = new ErrorResponse(message, violations);
        log.warn("{}: {}", message, error, e);
        return error;
    }

    /**
     * Пытается получить сообщение по errorCode.
     * <p>
     * Если сообщение не найдено, берет сообщение из Throwable.
     * <p>
     * Если сообщение из Throwable равно null, возвращает defaultMessage.
     *
     * @param errorCode
     * @param args           может быть null, если аргументов нет
     * @param e
     * @param defaultMessage
     * @return Сообщение связанное с errorCode, либо сообщение из Throwable, либо defaultMessage.
     */
    private String getErrorMessage(String errorCode, Object[] args, Throwable e, String defaultMessage) {
        String message;
        try {
            message = messageSource.getMessage(errorCode, args, Locale.getDefault());
        } catch (NoSuchMessageException ex) {
            log.warn("Message for error code = \"{}\" and args = \"{}\" wasn't found", errorCode, Arrays.toString(args));
            message = e.getMessage();
        }

        if (message == null) {
            log.warn("Exception didn't have a message", e);
            message = defaultMessage;
        }

        return message;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse onThrowable(final Throwable e) {
        log.error("Unexpected error occurred", e);
        return new ErrorResponse("Internal server error");
    }
}