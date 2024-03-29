package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.service.FriendshipService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final FriendshipService friendshipService;

    @Autowired
    public UserController(UserService userService, FriendshipService friendshipService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        friendshipService.addFriend(id, friendId);
        log.info("User with id={} and user with id={} became friends", id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable long id) {
        return friendshipService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getMutualFriends(@PathVariable long id, @PathVariable long otherId) {
        return friendshipService.getMutualFriends(id, otherId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable long id, @PathVariable long friendId) {
        friendshipService.removeFriend(id, friendId);
        log.info("User with id={} and user with id={} stopped being friends", id, friendId);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable long id) {
        return userService.getById(id);
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