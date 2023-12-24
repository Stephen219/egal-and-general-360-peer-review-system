package uk.cf.ac.LegalandGeneralTeam11.extension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import uk.cf.ac.LegalandGeneralTeam11.user.*;
import java.time.LocalDateTime;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class UserRepoTest2 {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserRepo userRepo;

    /**
     * Test generateActivationToken
     *
     */

    @Test
    public void testGenerateActivationToken() {
        User user = new User(1L, "test", "test@example.com", "password", 1L);
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any())).thenReturn(1);
        String token = userRepo.generateActivationToken(user, "reset");
        assertNotNull(token);
        verify(jdbcTemplate).update(anyString(), eq(user.getEmail()), eq(token), any(LocalDateTime.class), eq("reset"));
    }

    /**
     * test isTokenExpired
     */

    @Test
    public void testIsTokenExpired() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq("expiredToken"))).thenReturn(1);
        assertThrows(RuntimeException.class, () -> userRepo.isTokenExpired("expiredToken"));
        verify(jdbcTemplate).queryForObject(anyString(), eq(Integer.class), eq("expiredToken"));
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq("validToken"))).thenReturn(0);
        assertFalse(userRepo.isTokenExpired("validToken"));
        verify(jdbcTemplate).queryForObject(anyString(), eq(Integer.class), eq("validToken"));
        verify(jdbcTemplate, times(2)).queryForObject(anyString(), eq(Integer.class), any());
    }

    /**
     * test isTokenUsed
     */


    @Test
    public void testIsTokenUsed() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq("usedToken"))).thenReturn(1);
        assertThrows(RuntimeException.class, () -> userRepo.isTokenUsed("usedToken"));
        verify(jdbcTemplate).queryForObject(anyString(), eq(Integer.class), eq("usedToken"));
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq("unusedToken"))).thenReturn(0);
        assertFalse(userRepo.isTokenUsed("unusedToken"));
        verify(jdbcTemplate).queryForObject(anyString(), eq(Integer.class), eq("unusedToken"));
        verify(jdbcTemplate, times(2)).queryForObject(anyString(), eq(Integer.class), any());
    }
    /**
     * test activateUser
     */


    @Test
    public void testGetActivationToken() {
        String token = "testToken";
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(token))).thenReturn(Collections.singletonList(
                new TokenDto(1L, "test@example.com", token, LocalDateTime.now(), false, "reset")
        ));

        TokenDto result = userRepo.getActivationToken(token);

        assertNotNull(result);
        assertEquals(token, result.getToken());
        verify(jdbcTemplate).query(anyString(), any(RowMapper.class), eq(token));
    }

    /**
     * test setTokenUsed
     */

    @Test
    public void testSetTokenUsed() {
        when(jdbcTemplate.update(anyString(), eq("usedToken"))).thenReturn(1);
        userRepo.setTokenUsed("usedToken");
        verify(jdbcTemplate).update(anyString(), eq("usedToken"));
    }



    @Test
    public void testGetUserByEmail() {
        String email = "test@example.com";
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(email))).thenReturn(Collections.singletonList(
                new User(1L, "test", email, "password", 1L)
        ));
        User result = userRepo.getUserByEmail(email);
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(jdbcTemplate).query(anyString(), any(RowMapper.class), eq(email));
    }

    @Test
    public void testValidateOldPassword() {
        String email = "test@example.com";
        String oldPassword = "oldPassword";
        String encodedPassword = "encodedPassword";
        when(jdbcTemplate.queryForObject(anyString(), eq(String.class), eq(email))).thenReturn(encodedPassword);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        assertTrue(userRepo.validateOldPassword(oldPassword, email));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        assertThrows(RuntimeException.class, () -> userRepo.validateOldPassword(oldPassword, email));
    }

    @Test
    public void testCheckIfNewPasswordIsSameAsOldPassword() {
        String email = "test@example.com";
        String newPassword = "newPassword";
        String encodedPassword = "encodedPassword";
        when(jdbcTemplate.queryForObject(anyString(), eq(String.class), eq(email))).thenReturn(encodedPassword);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        assertThrows(RuntimeException.class, () -> userRepo.CheckIfNewPasswordIsSameAsOldPassword(newPassword, email));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        assertFalse(userRepo.CheckIfNewPasswordIsSameAsOldPassword(newPassword, email));
    }
}
