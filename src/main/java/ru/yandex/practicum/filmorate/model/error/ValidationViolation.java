package ru.yandex.practicum.filmorate.model.error;

import lombok.Data;

@Data
public class ValidationViolation {
    private final String field;
    private final String message;

    public ValidationViolation(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
