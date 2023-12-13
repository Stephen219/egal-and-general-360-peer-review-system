package uk.cf.ac.LegalandGeneralTeam11.domainTests;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uk.cf.ac.LegalandGeneralTeam11.domain.Domain;
import uk.cf.ac.LegalandGeneralTeam11.domain.DomainService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DomainControllerTest {

    @MockBean
    private DomainService domainService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    //Given
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void testDomainEditorPage() throws Exception {
        // When: Accessing the "/domain" page to view the domain editor
        mockMvc.perform(get("/domain"))
                // Then: The response status is OK, the view is "Domain/domainEditor",
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h1>Domain Editor</h1>")))
                .andExpect(content().string(containsString("<table class=\"table table-bordered\">")))
                .andExpect(content().string(containsString("Add Domain")))
                .andExpect(content().string(containsString("Edit")));
    }
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void testAddDomainPage() throws Exception {
        // Given: An admin is logged in

        // When: Accessing the "/domain/add" to add a new domain

        // Then: The response status is OK, the view is "Domain/addDomain",
        // and the model attribute "domain" exists
        mockMvc.perform(get("/domain/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("Domain/addDomain"))
                .andExpect(model().attributeExists("domain"));
    }
//    @Test
//    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
//    void testAddDomainPost() throws Exception {
//        CsrfToken csrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "abc123def456ghi789");
//
//        mockMvc.perform(post("/addDomain")
//                        .param("domain", "@example.com")
//                        .param("enabled", "true")
//                        .with(csrf().token(csrfToken)))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/domain"));
//    }

    @Test
    @WithMockUser(username ="admin", password = "admin", roles = "ADMIN")
    void testShowEditDomainForm() throws Exception {
        // Given
        Long domainId = 1L;
        Domain sampleDomain = new Domain();

        // Mock behavior of domainService
        when(domainService.getDomainById(domainId)).thenReturn(sampleDomain);

        // When
        mockMvc.perform(get("/domain/edit/{id}", domainId))
                .andExpect(status().isOk())
                .andExpect(view().name("Domain/editDomain"))
                .andExpect(model().attributeExists("domain"))
                .andExpect(model().attribute("domain", sampleDomain));

        // Then
        verify(domainService, times(1)).getDomainById(domainId);
    }
}



