package ru.yandex.practicum.filmorate.repository.user;

import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;
import java.util.Set;

public interface UserRepository {

    // Добавляет в друзья выбранного пользователя
    Collection<User> addFriend(int idUser, int idFriend);

    // Добавление пользователя
    User createUser(User user);

    // Удаляет из друзей выбранного пользователя
    Collection<User> deleteFriend(int idUser, int idFriend);

    // Получение всех пользователей
    Collection<User> findAllUsers();

    // Получение списка друзей пользователя
    Set<User> findFriends(int idUser);

    // Получение пользователя
    User findUser(int idUser);

    // Обновление пользователя
    User updateUser(User user);

}