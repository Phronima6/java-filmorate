package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    static final String FRIEND_ID = "friendId";
    static final String ID = "id";
    UserService userService;

    @PutMapping("/{" + ID + "}/friends/{" + FRIEND_ID + "}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> addFriend(@PathVariable(ID) @Positive int idUser,
                                      @PathVariable(FRIEND_ID) @Positive int idFriend) {
        return userService.addFriend(idUser, idFriend);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{" + ID + "}/friends/{" + FRIEND_ID + "}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> deleteFriend(@PathVariable(ID) @Positive int idUser,
                                         @PathVariable(FRIEND_ID) @Positive int idFriend) {
        return userService.deleteFriend(idUser, idFriend);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{" + ID + "}/friends")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> findFriends(@PathVariable(ID) @Positive int idUser) {
        return userService.findFriends(idUser);
    }

    @GetMapping("/{" + ID + "}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> findMutualFriends(@PathVariable(ID) @Positive int idUser,
                                              @PathVariable("otherId") @Positive int idFriend) {
        return userService.findMutualFriends(idUser, idFriend);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

}