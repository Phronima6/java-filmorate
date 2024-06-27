package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    @Override
    public Map<Integer, User> getUsers() {
        return users;
    }

    @Override
    public Collection<User> findAllUsers() {
        return users.values();
    }

    @Override
    public User createUser(User user) {
        user.setId(getNextId());
        log.info("InMemoryUserStorage, createUser, idUser created = {}.", user.getId());
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("InMemoryUserStorage, createUser, User created.");
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            log.warn("InMemoryUserStorage, updateUser, not found idUser = {}.", user.getId());
            throw new NotFoundException("Ошибка при попытке обновить пользователя. Не найден переданный"
                    + " id пользователя = " + user.getId() + ".");
        }
        log.info("InMemoryUserStorage, updateUser, User update.");
        return user;
    }

    @Override
    public int getNextId() {
        int currentMaxId = users.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}