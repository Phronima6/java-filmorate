package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping // Получение всех пользователей
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping // Добавление пользователя
    public User create(@Valid @RequestBody User user) {
        user.setId(getNextId());
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("Пользователь добавлен.");
        return user;
    }

    @PutMapping // Обновление пользователя
    public User update(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            log.error("Пользователь с id = {} не найден.",user.getId());
            throw new NotFoundException();
        }
        log.info("Пользователь обновлён.");
        return user;
    }

    // Генератор целочисленного идентификатора пользователя
    private int getNextId() {
        int currentMaxId = users.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}