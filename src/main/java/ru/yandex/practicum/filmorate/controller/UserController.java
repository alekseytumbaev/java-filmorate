package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.addFriend(id, friendId);
        log.info(
                String.format("User with id=%d and user with id=%d became friends", id, friendId)
        );
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable long id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getMutualFriends(@PathVariable long id, @PathVariable long otherId) {
        return userService.getMutualFriends(id, otherId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.removeFriend(id, friendId);
        log.info(
                String.format("User with id=%d and user with id=%d stopped being friends", id, friendId)
        );
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable long id) {
        return userService.getByIdIfExists(id);
    }

    @PostMapping
    public User add(@RequestBody @Valid User user) {
        User addedUser = userService.add(user);
        log.info(
                String.format("User with id=%d was added", addedUser.getId())
        );
        return addedUser;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        User updatedUser = userService.update(user);
        log.info(
                String.format("User with id=%d was updated", updatedUser.getId())
        );
        return updatedUser;
    }

    @GetMapping
    public ResponseEntity<Collection<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }
}