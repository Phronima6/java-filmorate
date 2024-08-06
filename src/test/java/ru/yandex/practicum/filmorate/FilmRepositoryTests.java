package ru.yandex.practicum.filmorate;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.RatingMpa;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.*;
import ru.yandex.practicum.filmorate.repository.film.JdbcFilmRepository;
import ru.yandex.practicum.filmorate.repository.mapper.FilmRowMapper;

@ExtendWith(MockitoExtension.class)
public class FilmRepositoryTests {

    @Mock
    NamedParameterJdbcOperations namedParameterJdbcOperations;
    @Mock
    FilmRowMapper filmRowMapper;
    @InjectMocks
    JdbcFilmRepository jdbcFilmRepository;
    Film film;

    @BeforeEach
    void setUp() {
        film = new Film();
        film.setId(1);
        film.setDescription("Test Description");
        film.setDuration(120);
        film.setName("Test Film");
        film.setReleaseDate(LocalDate.of(2023, 1, 1));
        RatingMpa ratingMpa = new RatingMpa();
        ratingMpa.setId(1);
        ratingMpa.setName("G");
        film.setMpa(ratingMpa);
    }

    @Test
    void testCreateFilm() {
        when(namedParameterJdbcOperations.update(anyString(), any(MapSqlParameterSource.class),
                any(GeneratedKeyHolder.class)))
                .thenAnswer(invocation -> {
                    GeneratedKeyHolder keyHolder = invocation.getArgument(2);
                    keyHolder.getKeyList().add(Collections.singletonMap("id", 1));
                    return 1;
                });
        Film createdFilm = jdbcFilmRepository.createFilm(film);
        assertNotNull(createdFilm);
        assertEquals(1, createdFilm.getId());
        verify(namedParameterJdbcOperations, times(1)).update(anyString(),
                any(MapSqlParameterSource.class), any(GeneratedKeyHolder.class));
    }

    @Test
    void testCreateLikeOfFilm() {
        when(namedParameterJdbcOperations.update(anyString(), any(Map.class))).thenReturn(1);
        when(namedParameterJdbcOperations.queryForObject(anyString(), any(Map.class),
                any(FilmRowMapper.class))).thenReturn(film);
        Film likedFilm = jdbcFilmRepository.createLikeOfFilm(1, 1);
        assertNotNull(likedFilm);
        assertEquals(1, likedFilm.getId());
        verify(namedParameterJdbcOperations, times(1)).update(anyString(), any(Map.class));
    }

    @Test
    void testDeleteLikeOfFilm() {
        when(namedParameterJdbcOperations.update(anyString(), any(Map.class))).thenReturn(1);
        when(namedParameterJdbcOperations.queryForObject(anyString(), any(Map.class),
                any(FilmRowMapper.class))).thenReturn(film);
        Film unlikedFilm = jdbcFilmRepository.deleteLikeOfFilm(1, 1);
        assertNotNull(unlikedFilm);
        assertEquals(1, unlikedFilm.getId());
        verify(namedParameterJdbcOperations, times(1)).update(anyString(), any(Map.class));
    }

    @Test
    void testFindAllFilms() {
        List<Film> films = Collections.singletonList(film);
        when(namedParameterJdbcOperations.query(anyString(), any(FilmRowMapper.class))).thenReturn(films);
        Collection<Film> foundFilms = jdbcFilmRepository.findAllFilms();
        assertNotNull(foundFilms);
        assertEquals(1, foundFilms.size());
        verify(namedParameterJdbcOperations, times(1))
                .query(anyString(), any(FilmRowMapper.class));
    }

    @Test
    void testFindFilm() {
        when(namedParameterJdbcOperations.queryForObject(anyString(), any(Map.class),
                any(FilmRowMapper.class))).thenReturn(film);
        Film foundFilm = jdbcFilmRepository.findFilm(1);
        assertNotNull(foundFilm);
        assertEquals(1, foundFilm.getId());
        verify(namedParameterJdbcOperations, times(1))
                .queryForObject(anyString(), any(Map.class), any(FilmRowMapper.class));
    }

    @Test
    void testUpdateFilm() {
        when(namedParameterJdbcOperations.update(anyString(), any(Map.class))).thenReturn(1);
        when(namedParameterJdbcOperations.queryForObject(anyString(), any(Map.class),
                any(FilmRowMapper.class))).thenReturn(film);
        Film updatedFilm = jdbcFilmRepository.updateFilm(film);
        assertNotNull(updatedFilm);
        assertEquals(1, updatedFilm.getId());
        verify(namedParameterJdbcOperations, times(1)).update(anyString(), any(Map.class));
    }

}