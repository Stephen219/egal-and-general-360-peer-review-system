package uk.cf.ac.LegalandGeneralTeam11.extension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import uk.cf.ac.LegalandGeneralTeam11.user.TokenDto;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testActivateAccountGet_ValidToken() {
        Long id = 1L;
        String validToken = "validToken";
        String userEmail = "user@example.com";
        LocalDateTime expiry = LocalDateTime.now().plusDays(1);
        Boolean isUsed = false;
        String type = "someType";
        TokenDto mockToken = new TokenDto(id, validToken, userEmail, expiry, isUsed, type);

        // Setup the behavior for userService.getActivationToken(validToken)
        when(userService.getActivationToken(validToken)).thenReturn(mockToken);
        when(userService.checkTokenUsable(validToken)).thenReturn(true);

        Model model = mock(Model.class);
        String result = userController.activateAccount(model, validToken);

        // Verify the expected interactions
        verify(model).addAttribute("token", "user@example.com");
        verify(model).addAttribute("mail", "validToken");
        assertEquals("activateAccount", result);


    }
}
