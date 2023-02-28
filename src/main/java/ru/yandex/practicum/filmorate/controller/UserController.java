package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private long nextId;
    private final Map<Long, User> users;
    private final MessageSource messageSource;

    public UserController(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.users = new HashMap<>();
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody @Valid User user) {
        save(user);
        log.info(messageSource.getMessage(
                "user_create",
                new Long[] {user.getId()},
                Locale.getDefault())
        );
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody @Valid User user) {
        try {
            renew(user);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(user, NOT_FOUND);
        }
        log.info(messageSource.getMessage(
                "user_update",
                new Long[] {user.getId()},
                Locale.getDefault()));

        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<Collection<User>> getAll() {
        return ResponseEntity.ok(users.values());
    }

    private void save(User user) {
        user.setId(getNextId());
        setLoginForEmptyName(user);
        users.put(user.getId(), user);
    }

    private void renew(User user) {
        if (!users.containsKey(user.getId()))
            throw new UserNotFoundException(
                    messageSource.getMessage(
                            "UserNotFoundException.message",
                            new Long[]{user.getId()},
                            Locale.getDefault())
            );
        setLoginForEmptyName(user);
        users.put(user.getId(), user);
    }

    private void setLoginForEmptyName(User user) {
        String name = user.getName();
        if (name == null || name.isBlank())
            user.setName(user.getLogin());
    }

    private long getNextId() {
        return ++nextId;
    }
}