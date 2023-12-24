package uk.cf.ac.LegalandGeneralTeam11.extension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.cf.ac.LegalandGeneralTeam11.user.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTests {

    @Mock
    private UserRepositoryInter usersRepository;

    @InjectMocks
    private UserServiceImpl userService;
    /**
     * This method is annotated with @BeforeEach, and it sets up the test environment
     * by initializing the mock objects using MockitoAnnotations.openMocks(this).
     */

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateActivationToken() {
        User user = new User();
        String type = "someType";
        when(usersRepository.generateActivationToken(user, type)).thenReturn("someToken");
        String result = userService.generateActivationToken(user, type);
        verify(usersRepository).generateActivationToken(user, type);
        assertEquals("someToken", result);
    }

    @Test
    void testCheckTokenUsable() {
        when(usersRepository.isTokenExpired("validToken")).thenReturn(false);
        when(usersRepository.isTokenUsed("validToken")).thenReturn(false);
        boolean result = userService.checkTokenUsable("validToken");
        verify(usersRepository).isTokenExpired("validToken");
        verify(usersRepository).isTokenUsed("validToken");

        assertTrue(result);
    }

    @Test
    void testGetActivationToken() {
        when(usersRepository.getActivationToken("someToken")).thenReturn(new TokenDto("someToken"));
        TokenDto result = userService.getActivationToken("someToken");
        verify(usersRepository).getActivationToken("someToken");
        assertNotNull(result);
        assertEquals("someToken", result.getToken());
    }

    @Test
    void testValidatePassword() {
        // Calling the actual method
        boolean result = userService.validatePassword("ValidPassword1@", "ValidPassword1@");

        // Assertions
        assertTrue(result);

        assertThrows(RuntimeException.class, () -> userService.validatePassword("Password1@", "DifferentPassword1@"));

        // Calling the method with an invalid password
        assertThrows(RuntimeException.class, () -> userService.validatePassword("invalidpassword", "invalidpassword"));
    }

    @Test
    void testSetTokenUsed() {
        userService.setTokenUsed("someToken");
        verify(usersRepository).setTokenUsed("someToken");
    }

    @Test
    void testGetUserByEmail() {
        when(usersRepository.getUserByEmail("user@example.com")).thenReturn(new User(0L,"test","user@example.com","password",0L));
        User result = userService.getUserByEmail("user@example.com");
        verify(usersRepository).getUserByEmail("user@example.com");
        assertNotNull(result);
        assertEquals("user@example.com", result.getEmail());
    }

    @Test
    void testValidateChangePassword() {
        when(usersRepository.validateOldPassword("oldPassword", "user@example.com")).thenReturn(true);
        when(usersRepository.CheckIfNewPasswordIsSameAsOldPassword("newPassword", "user@example.com")).thenReturn(false);
        boolean result = userService.validateChangePassword("oldPassword", "user@example.com", "newPassword");
        verify(usersRepository).validateOldPassword("oldPassword", "user@example.com");
        verify(usersRepository).CheckIfNewPasswordIsSameAsOldPassword("newPassword", "user@example.com");

        // Assertions
        assertTrue(result);
    }
}
