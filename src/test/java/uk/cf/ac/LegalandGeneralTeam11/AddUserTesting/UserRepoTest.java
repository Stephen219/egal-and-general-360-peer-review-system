package uk.cf.ac.LegalandGeneralTeam11.AddUserTesting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import uk.cf.ac.LegalandGeneralTeam11.user.User;
import uk.cf.ac.LegalandGeneralTeam11.user.UserRepo;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserRepoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserRepo userRepo;

    @BeforeEach
    void setUp() {
        userRepo.setUsersMapper();
    }

    @Test
    void testGetUser() {
        Long userId = 1L;
        User mockUser = new User(userId, "username", "email", "password", 2L);
        when(jdbcTemplate.queryForObject(any(String.class), any(RowMapper.class), eq(userId)))
                .thenReturn(mockUser);

        User result = userRepo.getUser(userId);
        assertNotNull(result);
        assertEquals(mockUser, result);
    }

    @Test
    void testGetUsers() {
        List<User> mockUserList = Collections.singletonList(new User(1L, "username", "email", "password", 2L));
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(mockUserList);

        List<User> result = userRepo.getUsers();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockUserList, result);
    }
    @Test
    void testSaveNewUser() {
        User newUser = new User(null, "newuser", "newemail@example.com", "password", 2L);
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(newUser.getEmail())))
                .thenReturn(0);  // Indicates user does not exist
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        userRepo.save(newUser);

        verify(jdbcTemplate, times(1)).update(
                anyString(),
                eq(newUser.getUsername()),
                eq(newUser.getEmail()),
                eq("encodedPassword"),
                eq(newUser.getRoleId())
        );
    }

    @Test
    void testUpdateUser() {
        User existingUser = new User(1L, "existinguser", "existingemail@example.com", "password", 2L);
        userRepo.updateUser(existingUser);

        verify(jdbcTemplate, times(1)).update(
                anyString(),
                eq(existingUser.getUsername()),
                eq(existingUser.getEmail()),
                eq(existingUser.getPassword()),
                eq(existingUser.getRoleId()),
                eq(existingUser.getId())
        );
    }


    @Test
    void testGetUserByEmail() {
        String email = "test@example.com";
        User mockUser = new User(1L, "testuser", email, "password", 2L);
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(email)))
                .thenReturn(mockUser);

        User result = userRepo.getUserEmail(email);

        assertNotNull(result);
        assertEquals(mockUser, result);
    }


    @Test
    void testInsertUser() {
        User newUser = new User(null, "newuser", "newemail@example.com", "password", 2L);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        userRepo.insert(newUser);

        verify(jdbcTemplate, times(1)).update(
                anyString(),
                eq(newUser.getUsername()),
                eq(newUser.getEmail()),
                eq("encodedPassword"),
                eq(newUser.getRoleId())
        );
    }

    @Test
    void testGetUserByUsername() {
        String username = "testuser";
        User mockUser = new User(1L, username, "email@example.com", "password", 2L);
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(username)))
                .thenReturn(mockUser);

        User result = userRepo.getUserByUserName(username);

        assertNotNull(result);
        assertEquals(mockUser, result);
    }
    void testEncodePassword() {
        User user = new User();
        user.setPassword("plainPassword");
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

        userRepo.encodePassword(user);

        assertEquals("encodedPassword", user.getPassword());
        verify(passwordEncoder, times(1)).encode("plainPassword");
    }
    @Test
    void testSaveUserAlreadyExists() {
        User user = new User(null, "username", "email@example.com", "password", 2L);
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(user.getEmail())))
                .thenReturn(1);  // Indicates user already exists

        assertThrows(RuntimeException.class, () -> userRepo.save(user));
        verify(jdbcTemplate, never()).update(anyString(), any(), any(), any(), any());
    }

    @Test
    void testGetUserByInvalidEmail() {
        String email = "nonexistent@example.com";
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(email)))
                .thenReturn(null);

        User result = userRepo.getUserEmail(email);

        assertNull(result);
    }
}
