package ru.yandex.practicum.filmorate.repository.genre;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.mapper.GenreRowMapper;
import java.util.*;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class JdbcGenreRepository implements GenreRepository {

    NamedParameterJdbcOperations namedParameterJdbcOperations;
    GenreRowMapper genreRowMapper;
    static String sqlAddFilmGenre = "INSERT INTO film_genre (id_film, id_genre)"
            + " VALUES (:id_film, :id_genre)";
    static String sqlDeleteFilmGenres = "DELETE FROM film_genre"
            + " WHERE id_film = :id_film";
    static String sqlFindAllGenres = "SELECT *"
            + " FROM genre";
    static String sqlFindGenres = "SELECT g.*"
            + " FROM genre g"
            + " INNER JOIN film_genre fg ON g.id_genre = fg.id_genre"
            + " WHERE fg.id_film = :id_film";
    static String sqlFindGenre = "SELECT *"
            + " FROM genre"
            + " WHERE id_genre = :id_genre";

    @Override
    public void addFilmGenre(int idFilm, Set<Genre> genres) {
        genres.forEach(genre -> {
            Map<String, Object> params = new HashMap<>();
            params.put("id_film", idFilm);
            params.put("id_genre", genre.getId());
            namedParameterJdbcOperations.update(sqlAddFilmGenre, params);
            log.info("JdbcFilmRepository, addFilmGenre, genre added.");
        });
    }

    @Override
    public void deleteFilmGenres(int idFilm) {
        Map<String, Object> params = new HashMap<>();
        params.put("id_film", idFilm);
        namedParameterJdbcOperations.update(sqlDeleteFilmGenres, params);
    }

    @Override
    public Collection<Genre> findAllGenres() {
        return namedParameterJdbcOperations.query(sqlFindAllGenres, genreRowMapper);
    }

    @Override
    public Set<Genre> findGenres(int idFilm) {
        Map<String, Object> params = new HashMap<>();
        params.put("id_film", idFilm);
        return new HashSet<>(namedParameterJdbcOperations.query(sqlFindGenres, params, genreRowMapper));
    }

    @Override
    public Optional<Genre> findGenre(int idGenre) {
        Map<String, Object> params = new HashMap<>();
        params.put("id_genre", idGenre);
        try {
            return Optional.ofNullable(namedParameterJdbcOperations
                    .queryForObject(sqlFindGenre, params, genreRowMapper));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

}