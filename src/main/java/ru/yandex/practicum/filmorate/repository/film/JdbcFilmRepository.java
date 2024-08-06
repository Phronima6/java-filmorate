package ru.yandex.practicum.filmorate.repository.film;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.DataException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.mapper.FilmRowMapper;
import java.util.*;
import java.sql.Date;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class JdbcFilmRepository implements FilmRepository {

    NamedParameterJdbcOperations namedParameterJdbcOperations;
    FilmRowMapper filmRowMapper;
    static String sqlCreateFilm = "INSERT INTO film_storage (description, duration, name,"
            + " id_rating_mpa, release_date)"
            + " VALUES (:description, :duration, :name, :id_rating_mpa, :release_date)";
    static String sqlCreateFilmWithoutRating = "INSERT INTO film_storage (description, duration, name,"
            + " release_date)"
            + " VALUES (:description, :duration, :name, :release_date)";
    static String sqlCreateLikeOfFilm = "INSERT INTO likes (id_film, id_user)"
            + " VALUES (:id_film, :id_user)";
    static String sqlDeleteLikeOfFilm = "DELETE FROM likes"
            + " WHERE id_film = :id_film AND id_user = :id_user";
    static String sqlFindAllFilms = "SELECT *"
            + " FROM film_storage";
    static String sqlFindFilm = "SELECT *"
            + " FROM film_storage"
            + " WHERE id_film = :id_film";
    static String sqlFindLikes = "SELECT id_user"
            + " FROM likes"
            + " WHERE id_film = :id_film";
    static String sqlUpdateFilm = "UPDATE film_storage"
            + " SET description = :description, duration = :duration, name = :name, id_rating_mpa = :id_rating_mpa,"
            + " release_date = :release_date"
            + " WHERE id_film = :id_film";

    @Override
    public Film createFilm(Film film) {
        boolean isRaiting = false;
        Map<String, Object> params = new HashMap<>();
        params.put("description", film.getDescription());
        params.put("duration", film.getDuration());
        params.put("name", film.getName());
        if (film.getMpa() != null) {
            isRaiting = true;
            params.put("id_rating_mpa", film.getMpa().getId());
        }
        params.put("release_date", film.getReleaseDate());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        if (isRaiting) {
            log.warn("Создаем фильм с рейтингом");
            namedParameterJdbcOperations.update(sqlCreateFilm, new MapSqlParameterSource(params), keyHolder);
        } else {
            log.warn("Создаем фильм без рейтинга");
            namedParameterJdbcOperations.update(sqlCreateFilmWithoutRating, new MapSqlParameterSource(params), keyHolder);
        }
        Integer idFilm = keyHolder.getKeyAs(Integer.class);
        if (idFilm == null) {
            log.warn("JdbcFilmRepository, createFilm.");
            throw new DataException("Не удалось сохранить данные.");
        }
        film.setId(idFilm);
        log.info("JdbcFilmRepository, createFilm, idFilm created = {}.", film.getId());
        return film;
    }

    @Override
    public Film createLikeOfFilm(int idFilm, int idUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("id_film", idFilm);
        params.put("id_user", idUser);
        namedParameterJdbcOperations.update(sqlCreateLikeOfFilm, params);
        log.info("JdbcFilmRepository, createLikeOfFilm, like added.");
        return findFilm(idFilm);
    }

    @Override
    public Film deleteLikeOfFilm(int idFilm, int idUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("id_film", idFilm);
        params.put("id_user", idUser);
        namedParameterJdbcOperations.update(sqlDeleteLikeOfFilm, params);
        log.info("JdbcFilmRepository, deleteLikeOfFilm, like deleted.");
        return findFilm(idFilm);
    }

    @Override
    public Collection<Film> findAllFilms() {
        List<Film> films = namedParameterJdbcOperations.query(sqlFindAllFilms, filmRowMapper);
        films.forEach(film -> film.setLikes(findLikes(film.getId())));
        return films;
    }

    @Override
    public Film findFilm(int idFilm) {
        Map<String, Object> params = new HashMap<>();
        params.put("id_film", idFilm);
        Film film = namedParameterJdbcOperations.queryForObject(sqlFindFilm, params, filmRowMapper);
        film.setLikes(findLikes(idFilm));
        return film;
    }

    @Override
    public Set<Integer> findLikes(int idFilm) {
        Map<String, Object> params = new HashMap<>();
        params.put("id_film", idFilm);
        List<Integer> likes = namedParameterJdbcOperations.queryForList(sqlFindLikes, params, Integer.class);
        return new HashSet<>(likes);
    }

    @Override
    public Film updateFilm(Film film) {
        Map<String, Object> params = new HashMap<>();
        params.put("id_film", film.getId());
        params.put("description", film.getDescription());
        params.put("duration", film.getDuration());
        params.put("name", film.getName());
        params.put("id_rating_mpa", film.getMpa().getId());
        params.put("release_date", Date.valueOf(film.getReleaseDate()));
        namedParameterJdbcOperations.update(sqlUpdateFilm, params);
        log.info("JdbcFilmRepository, updateFilm, Film updated.");
        return findFilm(film.getId());
    }

}