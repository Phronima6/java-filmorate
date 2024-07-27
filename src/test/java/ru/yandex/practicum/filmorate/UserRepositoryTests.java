package ru.yandex.practicum.filmorate;

import static org.junit.jupiter.api.Assertions.*;
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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.user.JdbcUserRepository;
import ru.yandex.practicum.filmorate.repository.mapper.UserRowMapper;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTests {

    @Mock
    NamedParameterJdbcOperations namedParameterJdbcOperations;
    @Mock
    UserRowMapper userRowMapper;
    @InjectMocks
    JdbcUserRepository jdbcUserRepository;
    User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setLogin("testLogin");
        user.setEmail("test@example.com");
        user.setBirthday(LocalDate.of(1990, 1, 1));
        user.setName("Test User");
    }

    @Test
    void testCreateUser() {
        when(namedParameterJdbcOperations.update(anyString(), any(MapSqlParameterSource.class),
                any(GeneratedKeyHolder.class)))
                .thenAnswer(invocation -> {
                    GeneratedKeyHolder keyHolder = invocation.getArgument(2);
                    keyHolder.getKeyList().add(Collections.singletonMap("id", 1));
                    return 1;
                });
        when(namedParameterJdbcOperations.queryForObject(anyString(), any(Map.class),
                any(UserRowMapper.class))).thenReturn(user);
        User createdUser = jdbcUserRepository.createUser(user);
        assertNotNull(createdUser);
        assertEquals(1, createdUser.getId());
        verify(namedParameterJdbcOperations, times(1)).update(anyString(),
                any(MapSqlParameterSource.class), any(GeneratedKeyHolder.class));
    }

    @Test
    void testAddFriend() {
        when(namedParameterJdbcOperations.update(anyString(), any(Map.class))).thenReturn(1);
        when(namedParameterJdbcOperations.query(anyString(), any(Map.class),
                any(UserRowMapper.class))).thenReturn(Collections.singletonList(user));
        Collection<User> friends = jdbcUserRepository.addFriend(1, 2);
        assertNotNull(friends);
        assertEquals(1, friends.size());
        verify(namedParameterJdbcOperations, times(1)).update(anyString(), any(Map.class));
    }

    @Test
    void testDeleteFriend() {
        when(namedParameterJdbcOperations.update(anyString(), any(Map.class))).thenReturn(1);
        when(namedParameterJdbcOperations.query(anyString(), any(Map.class),
                any(UserRowMapper.class))).thenReturn(Collections.singletonList(user));
        Collection<User> friends = jdbcUserRepository.deleteFriend(1, 2);
        assertNotNull(friends);
        assertEquals(1, friends.size());
        verify(namedParameterJdbcOperations, times(1)).update(anyString(),
                any(Map.class));
    }

    @Test
    void testFindAllUsers() {
        List<User> users = Collections.singletonList(user);
        when(namedParameterJdbcOperations.query(anyString(), any(UserRowMapper.class))).thenReturn(users);
        Collection<User> foundUsers = jdbcUserRepository.findAllUsers();
        assertNotNull(foundUsers);
        assertEquals(1, foundUsers.size());
        verify(namedParameterJdbcOperations, times(1)).query(anyString(),
                any(UserRowMapper.class));
    }

    @Test
    void testFindUser() {
        when(namedParameterJdbcOperations.queryForObject(anyString(), any(Map.class),
                any(UserRowMapper.class))).thenReturn(user);
        User foundUser = jdbcUserRepository.findUser(1);
        assertNotNull(foundUser);
        assertEquals(1, foundUser.getId());
        verify(namedParameterJdbcOperations, times(1)).queryForObject(anyString(),
                any(Map.class), any(UserRowMapper.class));
    }

}