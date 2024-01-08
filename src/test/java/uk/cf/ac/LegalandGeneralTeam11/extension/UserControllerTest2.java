package uk.cf.ac.LegalandGeneralTeam11.extension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import uk.cf.ac.LegalandGeneralTeam11.user.TokenDto;
import uk.cf.ac.LegalandGeneralTeam11.user.User;
import uk.cf.ac.LegalandGeneralTeam11.user.UserController;
import uk.cf.ac.LegalandGeneralTeam11.user.UserService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class UserControllerTest2 {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;
    @Mock
    private TokenDto tokenDto;

    @Mock
    private Model model;

    /**
     * This method is annotated with @BeforeEach, and it sets up the test environment
     * by initializing the mock objects using MockitoAnnotations.openMocks(this).
     */

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test activateAccount get valid token
     *
     */



    @Test
    void testActivateAccountGet_ValidToken() {
        Long id = 1L;
        String validToken = "validToken";
        String userEmail = "user@example.com";
        LocalDateTime expiry = LocalDateTime.now().plusDays(1);
        Boolean isUsed = false;
        String type = "someType";
        TokenDto mockToken = new TokenDto(id, validToken, userEmail, expiry, isUsed, type);


        when(userService.getActivationToken(validToken)).thenReturn(mockToken);
        when(userService.checkTokenUsable(validToken)).thenReturn(true);

        Model model = mock(Model.class);
        String result = userController.activateAccount(model, validToken);

        // Verify the expected interactions
        verify(model).addAttribute("token", "user@example.com");
        verify(model).addAttribute("mail", "validToken");
        assertEquals("activateAccount", result);


    }
    /**
     * Test activateAccount password validation fails
     *
     */




    @Test
    void activateAccount_PasswordValidationFails() {

        // Arrange
        String token = "validToken";
        String password = "invalidPassword";
        String confirmPassword = "invalidPassword";
        String mail = "user@example.com";

        when(userService.getActivationToken(token)).thenReturn(tokenDto);
        when(tokenDto.getUserEmail()).thenReturn(mail);
        when(userService.validatePassword(password, confirmPassword)).thenReturn(false);

        // Act
        String result = userController.activateAccount(model, token, password, confirmPassword, mail);

        // Assert
        verify(model).addAttribute("activationError", "Passwords do not match");
        assertEquals("activateAccount", result);
    }
    /**
     * Test activateAccount email comparison fails
     *
     */




    @Test
    void activateAccount_EmailComparisonFails() {
        // Arrange
        String token = "validToken";
        String password = "validPassword";
        String confirmPassword = "validPassword";
        String mail = "user@example.com";

        when(userService.getActivationToken(token)).thenReturn(tokenDto);
        when(tokenDto.getUserEmail()).thenReturn("anotherUser@example.com");

        // Act
        String result = userController.activateAccount(model, token, password, confirmPassword, mail);

        // Assert
        verify(model).addAttribute("mail", "anotherUser@example.com");
        verify(model).addAttribute("activationError", "error with the parameters.");
        assertEquals("activateAccount", result);
    }
    /**
     * Test password reset with a happy path
     *
     */


    @Test
    void testResetPassword_SuccessfulReset() {
        // Arrange
        String validToken = "validToken";
        String userEmail = "user@example.com";
        String newPassword = "newPassword@123";

        TokenDto mockToken = new TokenDto();
        mockToken.setToken(validToken);
        mockToken.setUserEmail(userEmail);

        // Mocking the behavior of your userService
        when(userService.getActivationToken(validToken)).thenReturn(mockToken);
        when(userService.checkTokenUsable(validToken)).thenReturn(true);
        when(userService.validatePassword(newPassword, newPassword)).thenReturn(true);

        User mockUser = new User();
        when(userService.getUserByEmail(userEmail)).thenReturn(mockUser);

        // Mocking the Model instance
        Model model = mock(Model.class);

        // Act
        userController.resetPassword(
                model,
                validToken,
                userEmail,
                newPassword,
                newPassword
        );


        // Assert that it interacts with the userService as expected
        verify(userService, times(1)).getActivationToken(validToken);
        verify(userService, times(1)).checkTokenUsable(validToken);
        verify(userService, times(1)).validatePassword(newPassword, newPassword);
        verify(userService, times(1)).getUserByEmail(userEmail);
        verify(userService, times(1)).encodePassword(mockUser);
        verify(userService, times(1)).updateUser(mockUser);
        verify(userService, times(1)).setTokenUsed(validToken);

    }

    /**
     * Test password reset eith mismatched passwords
     *
     */


    @Test
    void testResetPassword_MismatchedPasswords() {
        // Arrange
        String token = "validToken";
        String email = "user@example.com";
        String password = "newPassword";
        String confirmPassword = "wrongPassword";
        String type = "reset";

        TokenDto mockTokenDto = new TokenDto();
        mockTokenDto.setToken(token);
        mockTokenDto.setUserEmail(email);

        when(userService.getActivationToken(token)).thenReturn(mockTokenDto);
        when(userService.validatePassword(password, confirmPassword)).thenReturn(false);

        // Act
        Model model = mock(Model.class);
        String result = userController.resetPassword(model, token, email, password, confirmPassword);

        // Assert
        assertEquals("account/ResetPassword", result);

        // Verify interactions on the userService mock
        verify(userService, never()).encodePassword(any(User.class));
        verify(userService, never()).updateUser(any(User.class));
        verify(userService, never()).setTokenUsed(token);

        // Verify that specific attributes are added to the model
        verify(model).addAttribute(eq("token"), eq("validToken"));
        verify(model).addAttribute(eq("email"), eq("user@example.com"));
        verify(model).addAttribute(eq("resetError"), eq("Passwords do not match"));
    }





}
