
//package uk.cf.ac.LegalandGeneralTeam11;
//
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequest;
//import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestService;
//
//import java.util.Date;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class FormControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private FormRequestService formRequestService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void testAcceptForm() throws Exception {
//        // Mock data
//        long formId = 1L;
//        String username = "charlie";
//        Date requestDate = new Date();
//        Date updateDate = new Date();
//        String approvalStatus = "pending";
//
//        FormRequest formRequest = new FormRequest(formId, username, requestDate, updateDate, approvalStatus);
//
//        Mockito.when(formRequestService.getFormRequestById(formId)).thenReturn(formRequest);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/accept/{id}", formId)
//                        .with(SecurityMockMvcRequestPostProcessors.user("adminUser").roles("ADMIN")))
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin"));
//
//        Mockito.verify(formRequestService, Mockito.times(1)).getFormRequestById(formId);
//    }
//}