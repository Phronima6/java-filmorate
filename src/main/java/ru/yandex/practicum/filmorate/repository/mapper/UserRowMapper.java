package ru.yandex.practicum.filmorate.repository.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import ru.yandex.practicum.filmorate.model.User;

@Component
@Slf4j
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        try {
            User user = new User();
            user.setBirthday(resultSet.getDate("birthday").toLocalDate());
            user.setEmail(resultSet.getString("email"));
            user.setId(resultSet.getInt("id_user"));
            user.setLogin(resultSet.getString("login"));
            user.setName(resultSet.getString("name"));
            return user;
        } catch (SQLException exception) {
            log.error("UserRowMapper, mapRow", exception);
            return null;
        }
    }

}