package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;

public interface UserService {

    // Добавление в друзья выбранного пользователя
    Collection<User> addFriend(int idUser, int idFriend);

    // Проверка наличия пользователя в базе данных
    void checkUser(int idUser, String massageLog, String massageException);

    // Добавление пользователя
    User createUser(User user);

    // Удаление из друзей выбранного пользователя
    Collection<User> deleteFriend(int idUser, int idFriend);

    // Получение всех пользователей
    Collection<User> findAllUsers();

    // Возвращение списка пользователей являющихся друзьями
    Collection<User> findFriends(int idUser);

    // Возвращение списка друзей общих с другим пользователем
    Collection<User> findMutualFriends(int idUser, int idFriend);

    // Обновление пользователя
    User updateUser(User user);

}