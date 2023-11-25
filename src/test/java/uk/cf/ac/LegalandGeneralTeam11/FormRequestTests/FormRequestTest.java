package uk.cf.ac.LegalandGeneralTeam11.FormRequestTests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.servlet.FlashMap;
import org.springframework.test.web.servlet.MvcResult;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequest;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestRepoImpl;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestService;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class FormRequestTest {
    @Autowired
    private MockMvc mockMvc;

    //please dont tpuch this test, am implementing it
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @Test
    public void testGetForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/form/new"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.flash().attributeExists("flashMessage"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/account"))
                .andReturn() ;
        String flashMessage = (String) result.getFlashMap().get("flashMessage");
        assertEquals("your form request has been submited!", flashMessage);
    }




    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @Test
    public void testGetFormWithSecondAttempt() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/form/new"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.flash().attributeExists("flashMessage"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/account"))
                .andReturn();

        String flashMessage = (String) result.getFlashMap().get("flashMessage");
        assertEquals("your form request has been submited!", flashMessage);

// Perform the second request
        result = mockMvc.perform(get("/form/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.flash().attributeExists("flashError"))
                .andExpect(MockMvcResultMatchers.flash().attribute("flashError", "Hang on, you already have a pending request!"))
                .andReturn();
        String flashError = (String) result.getFlashMap().get("flashError");
        assertEquals("Hang on, you already have a pending request!", flashError);


    }










}
