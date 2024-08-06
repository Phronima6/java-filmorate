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
import ru.yandex.practicum.filmorate.repository.rating.JdbcRatingMpaRepository;
import ru.yandex.practicum.filmorate.repository.mapper.RatingMpaRowMapper;
import ru.yandex.practicum.filmorate.model.RatingMpa;

@ExtendWith(MockitoExtension.class)
public class RatingMpaRepositoryTests {

    @Mock
    NamedParameterJdbcOperations namedParameterJdbcOperations;
    @Mock
    RatingMpaRowMapper ratingMpaRowMapper;
    @InjectMocks
    JdbcRatingMpaRepository jdbcRatingMpaRepository;
    RatingMpa ratingMpa;

    @BeforeEach
    void setUp() {
        ratingMpa = new RatingMpa();
        ratingMpa.setId(1);
        ratingMpa.setName("G");
    }

    @Test
    void testFindAllRatingMpa() {
        List<RatingMpa> ratingMpas = Collections.singletonList(ratingMpa);
        when(namedParameterJdbcOperations.query(anyString(), any(RatingMpaRowMapper.class))).thenReturn(ratingMpas);
        Collection<RatingMpa> foundRatingMpas = jdbcRatingMpaRepository.findAllRatingMpa();
        assertNotNull(foundRatingMpas);
        assertEquals(1, foundRatingMpas.size());
        verify(namedParameterJdbcOperations, times(1)).query(anyString(),
                any(RatingMpaRowMapper.class));
    }

    @Test
    void testFindRatingMpa() {
        when(namedParameterJdbcOperations.queryForObject(anyString(), any(Map.class),
                any(RatingMpaRowMapper.class))).thenReturn(ratingMpa);
        Optional<RatingMpa> foundRatingMpa = jdbcRatingMpaRepository.findRatingMpa(1);
        assertTrue(foundRatingMpa.isPresent());
        assertEquals(1, foundRatingMpa.get().getId());
        verify(namedParameterJdbcOperations, times(1)).queryForObject(anyString(),
                any(Map.class), any(RatingMpaRowMapper.class));
    }

    @Test
    void testFindRatingMpaNotFound() {
        when(namedParameterJdbcOperations.queryForObject(anyString(), any(Map.class),
                any(RatingMpaRowMapper.class))).thenThrow(new EmptyResultDataAccessException(1));
        Optional<RatingMpa> foundRatingMpa = jdbcRatingMpaRepository.findRatingMpa(1);
        assertFalse(foundRatingMpa.isPresent());
        verify(namedParameterJdbcOperations, times(1)).queryForObject(anyString(),
                any(Map.class), any(RatingMpaRowMapper.class));
    }

}