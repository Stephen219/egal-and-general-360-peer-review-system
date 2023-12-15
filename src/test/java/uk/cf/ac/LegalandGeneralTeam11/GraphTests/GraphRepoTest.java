package uk.cf.ac.LegalandGeneralTeam11.GraphTests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.cf.ac.LegalandGeneralTeam11.Graphs.GraphRepoImpl;
import uk.cf.ac.LegalandGeneralTeam11.Graphs.GraphServiceImpl;
import uk.cf.ac.LegalandGeneralTeam11.domain.Domain;
import uk.cf.ac.LegalandGeneralTeam11.domain.DomainRepoImpl;
import uk.cf.ac.LegalandGeneralTeam11.domain.DomainServiceImpl;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class GraphRepoTest {

    @InjectMocks
    private GraphServiceImpl graphService;

    @Mock
    private GraphRepoImpl mockGraphRepo;

    @Mock
    private JdbcTemplate jdbcTemplate;


    @Test
    public void testGetCategoryAverages() {
        //given
        // Mocking JdbcTemplate
        JdbcTemplate mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
        GraphRepoImpl graphRepo = new GraphRepoImpl(mockJdbcTemplate);
        // Sample data
        String formId = "sampleFormId";
        // Mock the expected result from the database query
        List<Map<String, Object>> expectedAverages = List.of(
                Map.of("category", "Category1", "average", 4.5),
                Map.of("category", "Category2", "average", 3.2)
        );
        //when
        // Mocking behavior of the JdbcTemplate
        when(mockJdbcTemplate.queryForList(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(expectedAverages);

        // Call the method under test
        List<Map<String, Object>> resultAverages = graphRepo.getCategoryAverages(formId);
        //Then
        // Verify that the JdbcTemplate's queryForList method was called with the correct SQL and parameter
        verify(mockJdbcTemplate, Mockito.times(1)).queryForList(
                Mockito.anyString(), Mockito.eq(formId)
        );

        // Assert the result
        assertEquals(expectedAverages.size(), resultAverages.size());
    }
    @Test
    public void testGetChartData() {
        //Given
        // Mocking JdbcTemplate
        JdbcTemplate mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
        GraphRepoImpl graphRepo = new GraphRepoImpl(mockJdbcTemplate);

        // Sample data
        String formId = "sampleFormId";
        // Mock the expected result from the database query
        List<Map<String, Object>> expectedChartData = List.of(
                Map.of("category", "Category1", "relationship", "Relation1", "average", 4.5),
                Map.of("category", "Category2", "relationship", "Relation2", "average", 3.2)
        );
        //When
        // Mocking behavior of the JdbcTemplate
        when(mockJdbcTemplate.queryForList(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(expectedChartData);

        // Call the method under test
        List<Map<String, Object>> resultChartData = graphRepo.getChartData(formId);
        //Then
        // Verify that the JdbcTemplate's queryForList method was called with the correct SQL and parameter
        verify(mockJdbcTemplate, Mockito.times(1)).queryForList(
                Mockito.anyString(), Mockito.eq(formId)
        );

        // Assert the result
        assertEquals(expectedChartData.size(), resultChartData.size());
    }
    @Test
    public void testGetFormTextAnswer() {
        //Given
        // Mocking JdbcTemplate
        JdbcTemplate mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
        GraphRepoImpl graphRepo = new GraphRepoImpl(mockJdbcTemplate);

        // Sample data
        String formId = "sampleFormId";
        // Mock the expected result from the database query
        List<Map<String, Object>> expectedTextAnswers = List.of(
                Map.of("question", "Question1", "answer", "Answer1"),
                Map.of("question", "Question2", "answer", "Answer2"),
                Map.of("question", "Question3", "answer", "Answer3")
        );
        //When
        // Mocking behavior of the JdbcTemplate
        when(mockJdbcTemplate.queryForList(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(expectedTextAnswers);
        //Then
        // Call the method under test
        Map<String, List<String>> resultTextAnswers = graphRepo.getFormTextAnswer(formId);

        // Verify that the JdbcTemplate's queryForList method was called with the correct SQL and parameter
        verify(mockJdbcTemplate, Mockito.times(1)).queryForList(
                Mockito.anyString(), Mockito.eq(formId)
        );

        // Assert the result
        assertEquals(expectedTextAnswers.size(), resultTextAnswers.size());
    }
}
