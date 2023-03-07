package ru.yandex.practicum.filmorate.model.error;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {
    private String message;
    private List<ValidationViolation> validationViolations;

    public ErrorResponse(String message, List<ValidationViolation> violations) {
        this.message = message;
        this.validationViolations = violations;
    }

    public ErrorResponse(String message) {
        this(message, new ArrayList<>());
    }
}
