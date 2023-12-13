package uk.cf.ac.LegalandGeneralTeam11.questionTests;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uk.cf.ac.LegalandGeneralTeam11.questions.Question;
import uk.cf.ac.LegalandGeneralTeam11.questions.QuestionRepo;
import uk.cf.ac.LegalandGeneralTeam11.questions.QuestionRepoInter;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class QuestionControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private QuestionRepoInter questionRepoInter;

    //@Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void testFormQuestionPage() throws Exception {
        mvc.perform(get("/admin/questions"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.containsString("<h1>Question Editor</h1>")
                )).andExpect(MockMvcResultMatchers.content().string(
                        Matchers.containsString("Questions")
                ))
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.containsString("Add Question")
                ));

    }
    //@Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void testAddQuestionPage() throws Exception {
        mvc.perform(get("/admin/questions/add"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.containsString("<h1 class=\'text-center mb-4\'>Add Question</h1>")
                ));

    }
}
