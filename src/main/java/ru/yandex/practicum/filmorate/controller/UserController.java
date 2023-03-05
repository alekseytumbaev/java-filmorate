package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Locale;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MessageSource messageSource;

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable @Positive long id, @PathVariable @Positive long friendId) {
        userService.addFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable @Positive long id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getMutualFriends(@PathVariable @Positive long id, @PathVariable @Positive long otherId) {
        return userService.getMutualFriends(id, otherId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable @Positive long id, @PathVariable @Positive long friendId) {
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable @Positive long id) {
        return userService.getByIdIfExists(id);
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody @Valid User user) {
        userService.add(user);
        log.info(messageSource.getMessage(
                "user_create",
                new Long[]{user.getId()},
                Locale.getDefault())
        );
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody @Valid User user) {
        try {
            userService.update(user);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(user, NOT_FOUND);
        }
        log.info(messageSource.getMessage(
                "user_update",
                new Long[]{user.getId()},
                Locale.getDefault()));
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<Collection<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }
}