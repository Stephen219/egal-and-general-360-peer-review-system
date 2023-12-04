package uk.cf.ac.LegalandGeneralTeam11.FormRequestTests;

import org.apache.el.stream.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import uk.cf.ac.LegalandGeneralTeam11.Form.FormService;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequest;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestRepository;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestService;
import uk.cf.ac.LegalandGeneralTeam11.account.accountController;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc

public class FormAcceptanceTest {
    @Autowired
    private FormRequestRepository formRequestRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FormRequestService formRequestService;



    @Test
    public void testGetAllFormRequests() {
        // Arrange
        FormRequest formRequest = new FormRequest(10L, "charlie", new Date(), null, "pending");
        formRequestService.createFormRequest(formRequest);
        // Act
        List<FormRequest> formRequests = formRequestService.getAllFormRequests();
        // Assert
        Boolean result = formRequests.contains(formRequest);
        System.out.println(result);
    }



}
