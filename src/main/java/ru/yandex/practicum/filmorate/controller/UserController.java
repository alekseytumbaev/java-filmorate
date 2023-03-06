package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.addFriend(id, friendId);
        log.info("User with id={} and user with id={} became friends", id, friendId);
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
        log.info("User with id={} and user with id={} stopped being friends", id, friendId);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable long id) {
        return userService.getByIdIfExists(id);
    }

    @PostMapping
    public User add(@RequestBody @Valid User user) {
        User addedUser = userService.add(user);
        log.info("User with id={} was added", addedUser.getId());
        return addedUser;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        User updatedUser = userService.update(user);
        log.info("User with id={} was updated", updatedUser.getId());
        return updatedUser;
    }

    @GetMapping
    public Collection<User> getAll() {
        return userService.getAll();
    }
}