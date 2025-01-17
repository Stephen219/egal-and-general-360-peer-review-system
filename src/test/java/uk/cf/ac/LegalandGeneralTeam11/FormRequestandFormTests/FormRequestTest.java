package uk.cf.ac.LegalandGeneralTeam11.FormRequestandFormTests;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.context.support.WithMockUser;


import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class FormRequestTest {

    /**
     * this test is to check if the user can submit a form request
     */
    @Autowired
    private MockMvc mockMvc;
    @WithMockUser(username = "user", password = "password", roles = "USER")
    @Test
    public void testGetForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/form/new"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.flash().attributeExists("flashMessage"))
                .andExpect(redirectedUrl("/account"))
                .andReturn() ;
        String flashMessage = (String) result.getFlashMap().get("flashMessage");
        assertEquals("your form request has been submited!", flashMessage);
        System.out.println(flashMessage);

        System.out.println(result.getResponse().getContentAsString());

    }


    /**
     * this test is to check if the user can submit a form request more than twice
     * @throws Exception
     */

    @WithMockUser(username = "user", password = "password", roles = "USER")
    @Test
    public void testGetFormWithSecondAttempt() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/form/new"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.flash().attributeExists("flashMessage"))
                .andExpect(redirectedUrl("/account"))
                .andReturn();

        String flashMessage = (String) result.getFlashMap().get("flashMessage");
        assertEquals("your form request has been submited!", flashMessage);

        result = mockMvc.perform(get("/form/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.flash().attributeExists("flashError"))
                .andExpect(MockMvcResultMatchers.flash().attribute("flashError", "Hang on, you already have a pending request!"))
                .andReturn();
        String flashError = (String) result.getFlashMap().get("flashError");
        assertEquals("Hang on, you already have a pending request!", flashError);


    }

    /**
     * this test is to check if the user can submit a form request more than twice
     * @throws Exception
     */

    @Test
    @WithAnonymousUser
    public void whenAnonymousUserTriesToAccesRestrictedUrl() throws Exception {
        mockMvc.perform(get("/form/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }}
