package uk.cf.ac.LegalandGeneralTeam11.FormRequestandFormTests;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertTrue;


@SpringBootTest
@AutoConfigureMockMvc
public class FormControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void testFormRendering() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/review/{id}", "form1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("<input type=\"hidden\" name=\"formId\" value=\"form1\"/>"));
        assertTrue(content.contains("<form id=\"reviewForm\" action=\"/review/form1\" method=\"post\">"));



    }





}
