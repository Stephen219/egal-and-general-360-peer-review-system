package uk.cf.ac.LegalandGeneralTeam11.FormRequestTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.cf.ac.LegalandGeneralTeam11.Form.Form;
import uk.cf.ac.LegalandGeneralTeam11.Form.FormRepositoryImpl;
import uk.cf.ac.LegalandGeneralTeam11.Form.FormServiceImpl;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequest;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class CombinedFormTests {

    @Mock
    private FormRequestRepository formRequestRepository;

    @Mock
    private FormRepositoryImpl formRepository;

    @InjectMocks
    private FormServiceImpl formService;

    @Test
    void testGrantFormAccessAndGetUsernameForFormRequest() {
        // Arrange
        String username = "john_doe";
        LocalDate formDate = LocalDate.now();
        long formRequestId = 1L;

        // Mock behavior for grantFormAccess
        doNothing().when(formRepository).addForm(eq(username), eq(formDate));

        // Mock behavior for getUsernameForFormRequest
        FormRequest formRequest = new FormRequest(formRequestId, username, new Date(), new Date(), "pending");
        when(formRequestRepository.findFormRequestById(formRequestId)).thenReturn(formRequest);

        // Act
        formService.grantFormAccess(username, formDate);
        String result = formService.getUsernameForFormRequest(formRequestId);

        // Assert
        verify(formRepository, times(1)).addForm(eq(username), eq(formDate));
        assertEquals(username, result);
    }

    @Test
    void testUpdateFormRequestAndGetFormsAssignedToUser() {
        // Arrange
        long formRequestId = 1L;
        String username = "john_doe";
        FormRequest formRequest = new FormRequest(formRequestId, username, new Date(), new Date(), "pending");

        // Mock behavior for updateFormRequest
        doNothing().when(formRequestRepository).updateFormRequest(formRequest);

        // Mock behavior for getFormsAssignedToUser
        List<Form> assignedForms = Arrays.asList(
                new Form("1", username, LocalDate.now(), "pending"),
                new Form("2", username, LocalDate.now(), "pending")
        );
        when(formRepository.getFormsAssignedToUser(username)).thenReturn(assignedForms);

        // Act
        formService.updateFormRequest(formRequest);
        List<Form> result = formService.getFormsAssignedToUser(username);

        // Assert
        verify(formRequestRepository, times(1)).updateFormRequest(formRequest);
        verify(formRepository, times(1)).getFormsAssignedToUser(username);
        assertEquals(assignedForms, result);
    }

    // Add more combined tests...

}
