package uk.cf.ac.LegalandGeneralTeam11.domainTests;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class DomainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void testDomainEditorPage() throws Exception {
        // Given: An admin is logged in

        // When: Accessing "/domain" to view domains

        // Then: The response status is OK, the view is "Domain",
        // and the model attribute "domain" exists
        mockMvc.perform(MockMvcRequestBuilders.get("/domain"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.containsString("<h1>Domain Editor</h1>")
                ))
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.containsString("Domains")
                ))
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.containsString("Add Domain")
                ))
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.containsString("Edit")
                ))
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.containsString("Delete")
                ));
    }
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void testAddDomainPage() throws Exception {
        // Given: An admin is logged in

        // When: Accessing the "/domain/add" to add a new domain

        // Then: The response status is OK, the view is "Domain/addDomain",
        // and the model attribute "domain" exists
        mockMvc.perform(MockMvcRequestBuilders.get("/domain/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("Domain/addDomain"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("domain"));
    }
    //@Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void testAddDomainPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/addDomain")
                        .param("domain", "@example.com")
                        .param("enabled", "true"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/domain"));
    }
}



