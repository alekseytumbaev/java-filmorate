package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class ErrorResponse {
    public String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
