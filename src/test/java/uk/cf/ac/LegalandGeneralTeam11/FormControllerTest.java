package uk.cf.ac.LegalandGeneralTeam11;//package uk.cf.ac.LegalandGeneralTeam11;
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
//import org.springframework.security.test.context.support.WithMockUser;
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
//    @WithMockUser(username = "user", password = "password", roles = "ADMIN")
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
//        mockMvc.perform(MockMvcRequestBuilders.get("/accept/1", formId)
//                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin"));
//
//        Mockito.verify(formRequestService, Mockito.times(1)).getFormRequestById(formId);
//
//
//    }
//}
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uk.cf.ac.LegalandGeneralTeam11.Form.Form;
import uk.cf.ac.LegalandGeneralTeam11.Form.FormService;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequest;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestRepository;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestService;

import java.time.LocalDate;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
class FormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FormRequestRepository formRequestRepository;

    @Mock
    private FormService formService;

    @Mock
    private FormRequestService formRequestService;

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
////        Mockito.when(formRequestService.createFormRequest(formRequest)).thenReturn(formRequest);
////
////        Mockito.when(formRequestService.getFormRequestById(formId)).thenReturn(formRequest);
////        Mockito.when(formRequestRepository.findFormRequestById(formId)).thenReturn(formRequest);
//
//        // Mocking the behavior when creating a new form request
//        Mockito.doNothing().when(formRequestService).createFormRequest(Mockito.any(FormRequest.class));
//
//// Mocking the behavior when retrieving a form by ID
//        Mockito.when(formRequestService.getFormRequestById(formId)).thenReturn(formRequest);
//
//// Mocking the behavior of the repository to return the same form when finding by ID
//        Mockito.when(formRequestRepository.findFormRequestById(formId)).thenReturn(formRequest);
//
//// Perform your mockMvc request here
//
//// Verify that the method to create a new form request was called
//        Mockito.verify(formRequestService, Mockito.times(1)).createFormRequest(Mockito.any(FormRequest.class));
//
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/accept/{id}", formId)
//                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin"));
//
//        Mockito.verify(formService, Mockito.times(1)).grantFormAccess(Mockito.eq(username), Mockito.any(LocalDate.class));
//    }
@Test
void testAcceptForm() throws Exception {
    // Mock data
    long formId = 1L;
    String username = "charlie";
    Date requestDate = new Date();
    Date updateDate = new Date();
    String approvalStatus = "pending";

    FormRequest formRequest = new FormRequest(formId, username, requestDate, updateDate, approvalStatus);

    // Mocking the behavior when creating a new form request
    Mockito.doNothing().when(formRequestService).createFormRequest(Mockito.any(FormRequest.class));

    // Perform your mockMvc request here
    mockMvc.perform(MockMvcRequestBuilders.get("/accept/{id}", formId)
                    .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/admin"));

    // Verify that the method to create a new form request was called
    Mockito.verify(formRequestService, Mockito.times(1)).createFormRequest(Mockito.any(FormRequest.class));

    // Mocking the behavior when retrieving a form by ID
    Mockito.when(formRequestService.getFormRequestById(formId)).thenReturn(formRequest);

    // Mocking the behavior of the repository to return the same form when finding by ID
    Mockito.when(formRequestRepository.findFormRequestById(formId)).thenReturn(formRequest);

    // Verify other interactions as needed
    Mockito.verify(formService, Mockito.times(1)).grantFormAccess(Mockito.eq(username), Mockito.any(LocalDate.class));
}








}
