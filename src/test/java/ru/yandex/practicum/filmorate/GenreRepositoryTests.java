package ru.yandex.practicum.filmorate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.*;
import ru.yandex.practicum.filmorate.repository.genre.JdbcGenreRepository;
import ru.yandex.practicum.filmorate.repository.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.Genre;

@ExtendWith(MockitoExtension.class)
public class GenreRepositoryTests {

    @Mock
    NamedParameterJdbcOperations namedParameterJdbcOperations;
    @Mock
    GenreRowMapper genreRowMapper;
    @InjectMocks
    JdbcGenreRepository jdbcGenreRepository;
    Genre genre;

    @BeforeEach
    void setUp() {
        genre = new Genre();
        genre.setId(1);
        genre.setName("Test Genre");
    }

    @Test
    void testFindAllGenres() {
        List<Genre> genres = Collections.singletonList(genre);
        when(namedParameterJdbcOperations.query(anyString(), any(GenreRowMapper.class))).thenReturn(genres);
        Collection<Genre> foundGenres = jdbcGenreRepository.findAllGenres();
        assertNotNull(foundGenres);
        assertEquals(1, foundGenres.size());
        verify(namedParameterJdbcOperations, times(1)).query(anyString(),
                any(GenreRowMapper.class));
    }

    @Test
    void testFindGenres() {
        List<Genre> genres = Collections.singletonList(genre);
        when(namedParameterJdbcOperations.query(anyString(), any(Map.class),
                any(GenreRowMapper.class))).thenReturn(genres);
        Set<Genre> foundGenres = jdbcGenreRepository.findGenres(1);
        assertNotNull(foundGenres);
        assertEquals(1, foundGenres.size());
        verify(namedParameterJdbcOperations, times(1)).query(anyString(),
                any(Map.class), any(GenreRowMapper.class));
    }

    @Test
    void testFindGenre() {
        when(namedParameterJdbcOperations.queryForObject(anyString(), any(Map.class),
                any(GenreRowMapper.class))).thenReturn(genre);
        Optional<Genre> foundGenre = jdbcGenreRepository.findGenre(1);
        assertTrue(foundGenre.isPresent());
        assertEquals(1, foundGenre.get().getId());
        verify(namedParameterJdbcOperations, times(1)).queryForObject(anyString(),
                any(Map.class), any(GenreRowMapper.class));
    }

    @Test
    void testFindGenreNotFound() {
        when(namedParameterJdbcOperations.queryForObject(anyString(), any(Map.class),
                any(GenreRowMapper.class))).thenThrow(new EmptyResultDataAccessException(1));
        Optional<Genre> foundGenre = jdbcGenreRepository.findGenre(1);
        assertFalse(foundGenre.isPresent());
        verify(namedParameterJdbcOperations, times(1)).queryForObject(anyString(),
                any(Map.class), any(GenreRowMapper.class));
    }

}