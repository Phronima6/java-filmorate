package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface UserStorage {

    Map<Integer, User> users = new HashMap<>();

    // Получение доступа к хранилищу пользователей
    Map<Integer, User> getUsers();

    // Получение всех пользователей
    Collection<User> findAllUsers();

    // Добавление пользователя
    User createUser(User user);

    // Обновление пользователя
    User updateUser(User user);

    // Генератор целочисленного идентификатора пользователя
    int getNextId();

}