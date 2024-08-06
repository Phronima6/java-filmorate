package ru.yandex.practicum.filmorate.repository.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Slf4j
public class GenreRowMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        try {
            Genre genre = new Genre();
            genre.setId(resultSet.getInt("id_genre"));
            genre.setName(resultSet.getString("name"));
            return genre;
        } catch (SQLException exception) {
            log.error("GenreRowMapper, mapRow", exception);
            return null;
        }
    }

}