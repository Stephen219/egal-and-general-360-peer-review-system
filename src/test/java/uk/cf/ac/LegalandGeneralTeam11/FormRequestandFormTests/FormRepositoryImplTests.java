package uk.cf.ac.LegalandGeneralTeam11.FormRequestandFormTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.cf.ac.LegalandGeneralTeam11.Form.FormRepositoryImpl;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FormRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private FormRepositoryImpl formRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void updateReviewersAfterSubmission() {
        // Arrange
        String formId = "testFormId";
        String reviewer = "testReviewer";
        String relationship = "testRelationship";
        when(jdbcTemplate.update(anyString(), any(), any(), any())).thenReturn(1);

        // Act
        formRepository.updateReviewersAfterSubmission(formId, reviewer, relationship);

        // Assert
        verify(jdbcTemplate).update(anyString(), eq(relationship), eq(formId), eq(reviewer));
    }



    @Test
    void getIfHasFilledForm() {
        // Arrange
        String formId = "testFormId";
        String email = "testEmail";
        when(jdbcTemplate.queryForObject(anyString(), any(Class.class), any(), any())).thenReturn(true);

        // Act
        Boolean hasFilledForm = formRepository.getIfHasFilledForm(formId, email);

        // Assert
        assertTrue(hasFilledForm);
        verify(jdbcTemplate).queryForObject(anyString(), any(Class.class), eq(formId), eq(email));
    }

    @Test
    void ifUserHasSelfReviewed() {
        // Arrange
        String formId = "testFormId";
        String email = "testEmail";
        when(jdbcTemplate.queryForObject(anyString(), any(Class.class), any(), any())).thenReturn(1);

        // Act
        Boolean hasSelfReviewed = formRepository.ifUserHasSelfReviewed(formId, email);

        // Assert
        assertTrue(hasSelfReviewed);
        verify(jdbcTemplate).queryForObject(anyString(), any(Class.class), eq(formId), eq(email));
    }

}
