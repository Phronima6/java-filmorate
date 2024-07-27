package ru.yandex.practicum.filmorate.repository.rating;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.RatingMpa;
import ru.yandex.practicum.filmorate.repository.mapper.RatingMpaRowMapper;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class JdbcRatingMpaRepository implements RatingMpaRepository {

    NamedParameterJdbcOperations namedParameterJdbcOperations;
    RatingMpaRowMapper ratingMpaRowMapper;
    String sqlFindAllRatingMpa = "SELECT *"
            + " FROM rating_mpa";
    String sqlFindRatingMpa = "SELECT *"
            + " FROM rating_mpa"
            + " WHERE id_rating_mpa = :id_rating_mpa";

    @Override
    public Collection<RatingMpa> findAllRatingMpa() {
        return namedParameterJdbcOperations.query(sqlFindAllRatingMpa, ratingMpaRowMapper);
    }

    @Override
    public Optional<RatingMpa> findRatingMpa(int idRatingMpa) {
        Map<String, Object> params = new HashMap<>();
        params.put("id_rating_mpa", idRatingMpa);
        try {
            return Optional.ofNullable(namedParameterJdbcOperations
                    .queryForObject(sqlFindRatingMpa, params, ratingMpaRowMapper));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

}