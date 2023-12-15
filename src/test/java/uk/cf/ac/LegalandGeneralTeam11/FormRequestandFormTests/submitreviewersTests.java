package uk.cf.ac.LegalandGeneralTeam11.FormRequestTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class submitreviewersTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testSubmitReviewersEndpoint() throws Exception {
        String id = "form1";
        List<String> emails = Arrays.asList("email1@gmail.com", "email2@gmail.com");
        // Perform the POST request
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/submit_reviewers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("emails", emails.toArray(new String[0])))
                .andExpect(status().is3xxRedirection()) // Assuming you are redirecting upon successful submission
                .andReturn();
        // Verify the response
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(302, response.getStatus());
        //assertEquals("redirect:/review/" + id, response.getRedirectedUrl()); // Assuming you are redirecting to the correct URL
        assertEquals("/review/" + id, response.getRedirectedUrl()); // Assum

    }
}
