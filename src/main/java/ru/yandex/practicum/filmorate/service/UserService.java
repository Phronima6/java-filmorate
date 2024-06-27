package ru.yandex.practicum.filmorate.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    InMemoryUserStorage inMemoryUserStorage;

    public Collection<User> findAllUsers() {
        return inMemoryUserStorage.findAllUsers();
    }

    public User createUser(User user) {
        return inMemoryUserStorage.createUser(user);
    }

    public User updateUser(User user) {
        return inMemoryUserStorage.updateUser(user);
    }

    // Добавляет в друзья выбранного пользователя
    public Collection<User> addFriend(int idUser, int idFriend) {
        if (!inMemoryUserStorage.getUsers().containsKey(idUser)) {
            log.warn("UserService, addFriend, not found idUser.");
            throw new NotFoundException("Ошибка при попытке добавить друга. Не найден переданный"
                    + " id пользователя = " + idUser +  ".");
        }
        if (!inMemoryUserStorage.getUsers().containsKey(idFriend)) {
            log.warn("UserService, addFriend, not found idFriend.");
            throw new NotFoundException("Ошибка при попытке добавить друга. Не найден переданный"
                    + " id друга = " + idFriend + ".");
        }
        inMemoryUserStorage.getUsers().get(idUser).getFriends().add(idFriend);
        inMemoryUserStorage.getUsers().get(idFriend).getFriends().add(idUser);
        log.info("UserService, addFriend, friend added.");
        return new ArrayList<>(Arrays.asList(inMemoryUserStorage.getUsers().get(idUser),
                inMemoryUserStorage.getUsers().get(idFriend)));
    }

    // Удаляет из друзей выбранного пользователя
    public Collection<User> deleteFriend(int idUser, int idFriend) {
        if (!inMemoryUserStorage.getUsers().containsKey(idUser)) {
            log.warn("UserService, deleteFriend, not found idUser.");
            throw new NotFoundException("Ошибка при попытке удалить друга. Не найден переданный"
                    + " id пользователя = " + idUser +  ".");
        }
        if (!inMemoryUserStorage.getUsers().containsKey(idFriend)) {
            log.warn("UserService, deleteFriend, not found idFriend.");
            throw new NotFoundException("Ошибка при попытке удалить друга. Не найден переданный"
                    + " id друга = " + idFriend + ".");
        }
        inMemoryUserStorage.getUsers().get(idUser).getFriends().remove(idFriend);
        inMemoryUserStorage.getUsers().get(idFriend).getFriends().remove(idUser);
        log.info("UserService, deleteFriend, friend deleted.");
        return new ArrayList<>(Arrays.asList(inMemoryUserStorage.getUsers().get(idUser),
                inMemoryUserStorage.getUsers().get(idFriend)));
    }

    // Возвращает список пользователей являющихся друзьями
    public Collection<User> findAllFriends(int idUser) {
        if (!inMemoryUserStorage.getUsers().containsKey(idUser)) {
            log.warn("UserService, findAllFriends, not found idUser.");
            throw new NotFoundException("Ошибка при попытке получить список друзей. Не найден переданный"
                    + " id пользователя " + idUser + ".");
        }
        return inMemoryUserStorage.getUsers().get(idUser).getFriends().stream()
                .map(idFriend -> {
                    if (!inMemoryUserStorage.getUsers().containsKey(idFriend)) {
                        log.error("UserService, findAllFriends, not found idFriend.");
                        throw new NotFoundException("Ошибка при попытке получить список друзей."
                                + " Id друга " + idFriend + "нет в списке пользователей.");
                    }
                    return inMemoryUserStorage.getUsers().get(idFriend);
                })
                .collect(Collectors.toList());
    }

    // Возвращает список друзей общих с другим пользователем
    public Collection<User> findMutualFriends(int idUser, int idFriend) {
        if (!inMemoryUserStorage.getUsers().containsKey(idUser)) {
            log.warn("UserService, findMutualFriends, not found idUser.");
            throw new NotFoundException("Ошибка при попытке получить список общих друзей. Не найден переданный"
                    + " id пользователя = " + idUser +  ".");
        }
        if (!inMemoryUserStorage.getUsers().containsKey(idFriend)) {
            log.warn("UserService, findMutualFriends, not found idFriend.");
            throw new NotFoundException("Ошибка при попытке получить список общих друзей. Не найден переданный"
                    + " id друга = " + idFriend + ".");
        }
        return inMemoryUserStorage.getUsers().get(idUser).getFriends().stream()
                .filter(inMemoryUserStorage.getUsers().get(idFriend).getFriends()::contains)
                .map(id -> inMemoryUserStorage.getUsers().get(id))
                .collect(Collectors.toList());
    }

}