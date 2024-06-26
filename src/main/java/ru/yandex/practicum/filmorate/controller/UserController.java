package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;
import java.util.Optional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> addFriend(@PathVariable("id") Optional<Integer> idUser,
                           @PathVariable("friendId") Optional<Integer> idFriend) {
        if (!idUser.isPresent()) {
            log.warn("UserController, addFriend, not found idUser.");
            throw new ValidationException("Ошибка при попытке добавить друга. Не был указан id пользователя.");
        }
        if (!idFriend.isPresent()) {
            log.warn("UserController, addFriend, not found idFriend.");
            throw new ValidationException("Ошибка при попытке добавить друга. Не был указан id друга.");
        }
        if (idUser.get() < 0) {
            log.warn("UserController, addFriend, negative idUser = {}.", idUser.get());
            throw new ValidationException("Ошибка при попытке добавить друга. Был передан"
                    + " отрицательный id пользователя = " + idUser.get() + ".");
        }
        if (idFriend.get() < 0) {
            log.warn("UserController, addFriend, negative idFriend = {}.", idFriend.get());
            throw new ValidationException("Ошибка при попытке добавить друга. Был передан"
                    + " отрицательный id друга = " + idFriend.get() + ".");
        }
        return userService.addFriend(idUser.get(), idFriend.get());
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> deleteFriend(@PathVariable("id") Optional<Integer> idUser,
                                       @PathVariable("friendId") Optional<Integer> idFriend) {
        if (!idUser.isPresent()) {
            log.warn("UserController, deleteFriend, not found idUser.");
            throw new ValidationException("Ошибка при попытке удалить друга. Не был указан id пользователя.");
        }
        if (!idFriend.isPresent()) {
            log.warn("UserController, deleteFriend, not found idFriend.");
            throw new ValidationException("Ошибка при попытке удалить друга. Не был указан id друга.");
        }
        if (idUser.get() < 0) {
            log.warn("UserController, deleteFriend, negative idUser = {}.", idUser.get());
            throw new ValidationException("Ошибка при попытке удалить друга. Был передан"
                    + " отрицательный id пользователя = " + idUser.get() + ".");
        }
        if (idFriend.get() < 0) {
            log.warn("UserController, deleteFriend, negative idFriend = {}.", idFriend.get());
            throw new ValidationException("Ошибка при попытке удалить друга. Был передан"
                    + " отрицательный id друга = " + idFriend.get() + ".");
        }
        return userService.deleteFriend(idUser.get(), idFriend.get());
    }

    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> findAllFriends(@PathVariable("id") Optional<Integer> idUser) {
        if (!idUser.isPresent()) {
            log.warn("UserController, findAllFriends, not found idUser.");
            throw new ValidationException("Ошибка при попытке получить список друзей. Не был указан id пользователя.");
        }
        if (idUser.get() < 0) {
            log.warn("UserController, findAllFriends, negative idUser = {}.", idUser.get());
            throw new ValidationException("Ошибка при попытке получить список друзей. Был передан"
                    + " отрицательный id пользователя = " + idUser.get() + ".");
        }
        return userService.findAllFriends(idUser.get());
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> findMutualFriends(@PathVariable("id") Optional<Integer> idUser,
                                         @PathVariable("otherId") Optional<Integer> idFriend) {
        if (!idUser.isPresent()) {
            log.warn("UserController, findMutualFriends, not found idUser.");
            throw new ValidationException("Ошибка при попытке получить список общих друзей."
                    + " Не был указан id пользователя.");
        }
        if (!idFriend.isPresent()) {
            log.warn("UserController, findMutualFriends, negative idUser = {}.", idUser.get());
            throw new ValidationException("Ошибка при попытке получить список общих друзей."
                    + " Не был указан id друга.");
        }
        if (idUser.get() < 0) {
            log.warn("UserController, findMutualFriends, not found idFriend.");
            throw new ValidationException("Ошибка при попытке получить список общих друзей. Был передан"
                    + " отрицательный id пользователя = " + idUser.get() + ".");
        }
        if (idFriend.get() < 0) {
            log.warn("UserController, findMutualFriends, negative idFriend = {}.", idFriend.get());
            throw new ValidationException("Ошибка при попытке получить список общих друзей. Был передан"
                    + " отрицательный id друга = " + idFriend.get() + ".");
        }
        return userService.findMutualFriends(idUser.get(), idFriend.get());
    }

}