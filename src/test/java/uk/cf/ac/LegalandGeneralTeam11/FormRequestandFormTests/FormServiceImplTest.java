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
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class FormServiceImplTest {

    @Mock
    private FormRequestRepository formRequestRepository;

    @Mock
    private FormRepositoryImpl formRepository;

    @InjectMocks
    private FormServiceImpl formService;

    @Test
    void grantFormAccess() {
        // Arrange
        String username = "john_doe";
        LocalDate formDate = LocalDate.now();

        // Act
        formService.grantFormAccess(username, formDate);

        // Assert
        verify(formRepository, times(1)).addForm(eq(username), eq(formDate));
    }

    @Test
    void getUsernameForFormRequest() {
        // Arrange
        long formRequestId = 1L;
        FormRequest formRequest = new FormRequest(formRequestId, "user", new Date(), new Date(), "pending");
        when(formRequestRepository.findFormRequestById(formRequestId)).thenReturn(formRequest);

        // Act
        String result = formService.getUsernameForFormRequest(formRequestId);


        // Assert
        assertEquals(formRequest.getUsername(), result);
    }

    @Test
    void updateFormRequest() {
        // Arrange
        FormRequest formRequest = new FormRequest(1L, "user", new Date(), new Date(), "pending");

        // Act
        formService.updateFormRequest(formRequest);

        // Assert
        verify(formRequestRepository, times(1)).updateFormRequest(formRequest);
    }


}

