package ru.yandex.practicum.filmorate.repository.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Slf4j
public class FilmRowMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        try {
            Film film = new Film();
            film.setDescription(resultSet.getString("description"));
            film.setDuration(resultSet.getInt("duration"));
            film.setId(resultSet.getInt("id_film"));
            film.setIdRatingMpa(resultSet.getInt("id_rating_mpa"));
            film.setName(resultSet.getString("name"));
            film.setReleaseDate((resultSet.getDate("release_date").toLocalDate()));
            return film;
        } catch (SQLException exception) {
            log.error("FilmRowMapper, mapRow", exception);
            return null;
        }
    }

}