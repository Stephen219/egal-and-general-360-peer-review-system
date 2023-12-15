package uk.cf.ac.LegalandGeneralTeam11.questionTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.cf.ac.LegalandGeneralTeam11.questions.Question;
import uk.cf.ac.LegalandGeneralTeam11.questions.QuestionRepoInter;
import uk.cf.ac.LegalandGeneralTeam11.questions.QuestionService;
import uk.cf.ac.LegalandGeneralTeam11.user.User;
import uk.cf.ac.LegalandGeneralTeam11.user.UserRepositoryInter;

import static org.mockito.Mockito.*;

public class QuestionServiceTest {
    @Mock
    private QuestionRepoInter questionRepoInter;
    @InjectMocks
    private QuestionService questionService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddQuestion() {
        Question question = new Question(100L, "content", "category");
        doNothing().when(questionRepoInter).addQuestion(question);

        questionService.addQuestion(question);

        verify(questionRepoInter, times(1)).addQuestion(question);
    }
}
