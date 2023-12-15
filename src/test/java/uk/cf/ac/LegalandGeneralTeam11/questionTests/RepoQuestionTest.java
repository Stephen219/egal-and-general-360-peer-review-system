package uk.cf.ac.LegalandGeneralTeam11.questionTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import uk.cf.ac.LegalandGeneralTeam11.questions.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RepoQuestionTest {
    @Mock
    QuestionServiceInter questionServiceInter;
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private QuestionRepo questionRepo;
    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        questionRepo.setQuestionMapper();}
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void testAddQuestion() throws Exception{
        Question question = new Question(null,"content","category");

        doAnswer(invocation -> {
            return null;
        }).when(jdbcTemplate).update(anyString(),any(),any());

        questionRepo.addQuestion(question);
        verify(jdbcTemplate).update("INSERT INTO questions (question, category) VALUES (?, ?)", "content", "category");
    }
}
