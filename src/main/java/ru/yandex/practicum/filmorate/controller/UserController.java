package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private long nextId;
    private final Map<Long, User> users;

    public UserController() {
        this.users = new HashMap<>();
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody @Valid User user) {
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("Добавлен пользователь с id = {}", user.getId());
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody @Valid User user) {
        users.put(user.getId(), user);
        log.info("Обновлен пользователь с id = {}", user.getId());
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<Collection<User>> getAll() {
        return ResponseEntity.ok(users.values());
    }

    private long getNextId() {
        nextId++;
        return nextId;
    }
}
