package uk.cf.ac.LegalandGeneralTeam11.extension;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import uk.cf.ac.LegalandGeneralTeam11.user.TokenDto;
import uk.cf.ac.LegalandGeneralTeam11.user.User;
import uk.cf.ac.LegalandGeneralTeam11.user.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private UserService userService;


    /**
     * Test for activateAccount method in UserController
     * it uses mockmvc to perform a get request to the endpoint
     * @throws Exception
     */



    @Test
    @WithMockUser(username = "testuser", password = "testpassword", roles = "USER")
    public void testActivateAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/activate/{token}", "testToken"))
                .andExpect(status().isOk())
                .andExpect(view().name("activateAccount"));
    }
    /**
     * Test for activateAccount method in UserController
     * it uses mockmvc to perform a post request to the endpoint
     * @throws Exception
     */

    @Test
    @WithMockUser(username = "testuser", password = "testpassword", roles = "USER")
    public void testResetPasswordForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/forgot-password"))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/resetPasswordEmailForm"));
    }

    @Test
    public void testResetPassword() throws Exception {
        mockMvc.perform(post("/forgot-password")
                        .param("email", "test@example.com")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/resetPasswordEmailForm"));
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpassword", roles = "USER")
    public void testUpdatePasswordForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/my_info/update-password"))
                .andExpect(status().isOk())
                .andExpect(view().name("account/update_password"));
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpassword", roles = "USER")
    public void testUpdatePassword() throws Exception {
        mockMvc.perform(post("/my_info/update-password")
                        .param("oldPassword", "testpassword")
                        .param("newPassword", "MyNewpassword@123")
                        .param("confirmPassword", "MyNewpassword@123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test for resetPassword method in UserController
     * @throws Exception
     */



    @Test
    void resetPassword_SuccessfulReset() throws Exception {
        // Arrange
        when(userService.getActivationToken(anyString())).thenReturn(new TokenDto("validToken"));
        when(userService.validatePassword(anyString(), anyString())).thenReturn(true);
        when(userService.checkTokenUsable(anyString())).thenReturn(true);
        when(userService.getUserByEmail(anyString())).thenReturn(new User());
        mockMvc.perform(post("/password/reset-password")
                        .param("token", "valid_token")
                        .param("email", "test@example.com")
                        .param("password", "newPassword")
                        .param("confirmPassword", "newPassword")
                        .with(csrf()))
                .andExpect(status().isOk());
    }


}
