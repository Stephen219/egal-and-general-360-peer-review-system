package uk.cf.ac.LegalandGeneralTeam11.extension;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "testuser", password = "testpassword", roles = "USER")
    public void testActivateAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/activate/{token}", "testToken"))
                .andExpect(status().isOk())
                .andExpect(view().name("activateAccount"));
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpassword", roles = "USER")
    public void testResetPasswordForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/forgot-password"))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/resetPasswordEmailForm"));
    }

    @Test
    public void testResetPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/forgot-password")
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
        mockMvc.perform(MockMvcRequestBuilders.post("/my_info/update-password")
                        .param("oldPassword", "testpassword")
                        .param("newPassword", "MyNewpassword@123")
                        .param("confirmPassword", "MyNewpassword@123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print());
    }


}
