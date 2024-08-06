package ru.yandex.practicum.filmorate.repository.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.RatingMpa;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Slf4j
public class RatingMpaRowMapper implements RowMapper<RatingMpa> {

    @Override
    public RatingMpa mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        try {
            RatingMpa ratingMpa = new RatingMpa();
            ratingMpa.setId(resultSet.getInt("id_rating_mpa"));
            ratingMpa.setName(resultSet.getString("name"));
            return ratingMpa;
        } catch (SQLException exception) {
            log.error("RatingMpaRowMapper, mapRow", exception);
            return null;
        }
    }

}