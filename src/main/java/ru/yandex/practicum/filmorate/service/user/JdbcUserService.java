package ru.yandex.practicum.filmorate.service.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;
import java.util.Collection;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
@Slf4j
public class JdbcUserService implements UserService {

    UserRepository userRepository;

    @Override
    public Collection<User> addFriend(int idUser, int idFriend) {
        String massageLog = "UserService, addFriend.";
        String massageException = "Ошибка при попытке добавить друга. Не найден переданный id = ";
        checkUser(idUser, massageLog, massageException);
        checkUser(idFriend, massageLog, massageException);
        if (userRepository.findFriends(idUser).stream()
                .anyMatch(user -> user.getId() == idFriend)) {
            log.warn(massageLog);
            throw new DataException("Ошибка при попытке добавить друга. Такой друг idUser = "
                    + idFriend + " уже есть в списке базы данных.");
        }
        userRepository.addFriend(idUser, idFriend);
        log.info("UserService, addFriend, friend added.");
        return userRepository.findFriends(idUser);
    }

    @Override
    public void checkUser(int idUser, String massageLog, String massageException) {
        if (userRepository.findAllUsers().stream()
                .map(User::getId)
                .noneMatch(id -> id == idUser)) {
            log.warn(massageLog);
            throw new NotFoundException(massageException + idUser +  ".");
        }
    }

    @Override
    public User createUser(User user) {
        if (userRepository.findAllUsers().stream()
                .map(User::getEmail)
                .anyMatch(email -> email.equals(user.getEmail()))
                || userRepository.findAllUsers().stream()
                .map(User::getLogin)
                .anyMatch(login -> login.equals(user.getLogin()))) {
            log.warn("UserService, createUser.");
            throw new DataException("Ошибка при попытке добавить пользователя. Пользователь с таким email = "
                    + user.getEmail() + " или login = " + user.getLogin() + " уже есть.");
        }
        return userRepository.createUser(user);
    }

    @Override
    public Collection<User> deleteFriend(int idUser, int idFriend) {
        String massageLog = "UserService, deleteFriend.";
        String massageException = "Ошибка при попытке удалить друга. Не найден переданный id = ";
        checkUser(idUser, massageLog, massageException);
        checkUser(idFriend, massageLog, massageException);
        if (userRepository.findFriends(idUser).stream()
                .noneMatch(user -> user.getId() == idFriend)) {
            log.warn(massageLog);
        }
        userRepository.deleteFriend(idUser, idFriend);
        log.info("UserService, deleteFriend, friend deleted.");
        return userRepository.findFriends(idUser);
    }

    @Override
    public Collection<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public Collection<User> findFriends(int idUser) {
        String massageLog = "UserService, findFriends.";
        String massageException = "Ошибка при попытке получить список друзей. Не найден переданный id = ";
        checkUser(idUser, massageLog, massageException);
        return userRepository.findFriends(idUser);
    }

    @Override
    public Collection<User> findMutualFriends(int idUser, int idFriend) {
        String massageLog = "UserService, findMutualFriends.";
        String massageException = "Ошибка при попытке получить список общих друзей. Не найден переданный id = ";
        checkUser(idUser, massageLog, massageException);
        checkUser(idFriend, massageLog, massageException);
        return userRepository.findFriends(idUser).stream()
                .filter(userRepository.findFriends(idFriend)::contains)
                .collect(Collectors.toList());
    }

    @Override
    public User updateUser(User user) {
        String massageLog = "UserService, updateUser.";
        String massageException = "Ошибка при попытке обновить пользователя. Не найден переданный id = ";
        checkUser(user.getId(), massageLog, massageException);
        userRepository.updateUser(user);
        return userRepository.findUser(user.getId());
    }

}