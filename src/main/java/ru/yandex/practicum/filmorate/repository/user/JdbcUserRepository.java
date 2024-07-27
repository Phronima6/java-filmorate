package ru.yandex.practicum.filmorate.repository.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.DataException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.mapper.UserRowMapper;
import java.util.*;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class JdbcUserRepository implements UserRepository {

    NamedParameterJdbcOperations namedParameterJdbcOperations;
    UserRowMapper userRowMapper;
    static String sqlAddFriend = "INSERT INTO friends (id_user, id_friend)"
            + " VALUES (:id_user, :id_friend)";
    static String sqlCreateUser = "INSERT INTO user_storage (birthday, email, login, name)"
            + " VALUES (:birthday, :email, :login, :name)";
    static String sqlDeleteFriend = "DELETE FROM friends"
            + " WHERE id_user = :id_user AND id_friend = :id_friend";
    static String sqlFindAllUsers = "SELECT *"
            + " FROM user_storage";
    static String sqlFindFriends = "SELECT *"
            + " FROM user_storage"
            + " WHERE id_user IN ("
            + "SELECT id_friend"
            + " FROM friends"
            + " WHERE id_user = :id_user)";
    static String sqlFindUser = "SELECT *"
            + " FROM user_storage"
            + " WHERE id_user = :id_user";
    static String sqlUpdateUser = "UPDATE user_storage"
            + " SET birthday = :birthday, email = :email, login = :login, name = :name"
            + " WHERE id_user = :id_user";

    @Override
    public Collection<User> addFriend(int idUser, int idFriend) {
        Map<String, Object> params = new HashMap<>();
        params.put("id_user", idUser);
        params.put("id_friend", idFriend);
        namedParameterJdbcOperations.update(sqlAddFriend, params);
        log.info("JdbcUserRepository, addFriend, friend added.");
        return findFriends(idUser);
    }

    @Override
    public User createUser(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("login", user.getLogin());
        params.put("email", user.getEmail());
        params.put("birthday", user.getBirthday());
        params.put("name", user.getName() == null ? user.getLogin() : user.getName());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update(sqlCreateUser, new MapSqlParameterSource(params), keyHolder);
        Integer idUser = keyHolder.getKeyAs(Integer.class);
        if (idUser == null) {
            log.warn("JdbcUserRepository, createUser.");
            throw new DataException("Ошибка при попытке сохранить данные пользователя.");
        }
        user.setId(idUser);
        log.info("JdbcUserRepository, createUser, user created.");
        return findUser(idUser);
    }

    @Override
    public Collection<User> deleteFriend(int idUser, int idFriend) {
        Map<String, Object> params = new HashMap<>();
        params.put("id_user", idUser);
        params.put("id_friend", idFriend);
        namedParameterJdbcOperations.update(sqlDeleteFriend, params);
        log.info("JdbcUserRepository, deleteFriend, friend deleted.");
        return findFriends(idUser);
    }

    @Override
    public Collection<User> findAllUsers() {
        List<User> users = namedParameterJdbcOperations.query(sqlFindAllUsers, userRowMapper);
        users.forEach(user -> user.setFriends(findFriends(user.getId())));
        return users;
    }

    @Override
    public Set<User> findFriends(int idUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("id_user", idUser);
        return new HashSet<>(namedParameterJdbcOperations.query(sqlFindFriends, params, userRowMapper));
    }

    @Override
    public User findUser(int idUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("id_user", idUser);
        User user = namedParameterJdbcOperations.queryForObject(sqlFindUser, params, userRowMapper);
        user.setFriends(findFriends(idUser));
        return user;
    }

    @Override
    public User updateUser(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("id_user", user.getId());
        params.put("birthday", user.getBirthday());
        params.put("email", user.getEmail());
        params.put("login", user.getLogin());
        params.put("name", user.getName());
        namedParameterJdbcOperations.update(sqlUpdateUser, params);
        log.info("JdbcUserRepository, updateUser, user updated.");
        return user;
    }

}