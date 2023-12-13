package uk.cf.ac.LegalandGeneralTeam11;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import uk.cf.ac.LegalandGeneralTeam11.Graphs.GraphController;
import uk.cf.ac.LegalandGeneralTeam11.Graphs.GraphService;
import uk.cf.ac.LegalandGeneralTeam11.answers.AnswerService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest
@AutoConfigureMockMvc
public class GraphTests {
        @Autowired
        private MockMvc mockMvc;
        @Mock
        private GraphService graphService;

        @Mock
        private AnswerService answerService;

        @InjectMocks
        private GraphController graphController;

        @BeforeEach
        public void setup() {
            mockMvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .build();
        }
        @Autowired
        private WebApplicationContext context;

        @Test
        @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
        public void testGetResults() {
            //Given an admin is logged in
            //When accessing "/admin/results/{id}" to view the results
            //Then the response status is OK, the view is "admin/results/{id}",
            // and the model attributes are added successfully
            //and the graphService and answerService methods were called with the correct parameters

            // Sample data for formId
            String formId = "formId";
            // Sample data for graphInt
            List<Map<String, Object>> graphInt = new ArrayList<>();
            Map<String, Object> graphData1 = new HashMap<>();
            graphData1.put("category", "Category1");
            graphData1.put("average", 4.5);
            graphInt.add(graphData1);

            Map<String, Object> graphData2 = new HashMap<>();
            graphData2.put("category", "Category2");
            graphData2.put("average", 3.8);
            graphInt.add(graphData2);

            // Sample data for graphString
            Map<String, List<String>> graphString = new HashMap<>();
            List<String> answers1 = Arrays.asList("Answer1", "Answer2", "Answer3");
            graphString.put("Question1", answers1);

            List<String> answers2 = Arrays.asList("Answer4", "Answer5", "Answer6");
            graphString.put("Question2", answers2);

            List<String> reviewers = Arrays.asList("reviewer1@example.com", "reviewer2@example.com", "reviewer3@example.com");

            // Mocking behavior of graphService and answerService methods
            when(graphService.getCategoryAverages(formId)).thenReturn(graphInt);
            when(graphService.getFormTextAnswer(formId)).thenReturn(graphString);
            when(answerService.GetAllReviewers(formId)).thenReturn(reviewers);

            Model model = mock(Model.class);

            // Call the controller method
            String viewName = graphController.getResults(formId, model);

            // Verify that the controller method returns the expected view name
            assertEquals("account/seeAllResults", viewName);

            // Verify that the model attributes are added correctly
            verify(model, times(1)).addAttribute("Reviewers", reviewers);
            verify(model, times(1)).addAttribute("NumberAnswer", graphInt);
            verify(model, times(1)).addAttribute("TextAnswer", graphString);

            // Verify that graphService and answerService methods were called with the correct parameters
            verify(graphService, times(1)).getCategoryAverages(formId);
            verify(graphService, times(1)).getFormTextAnswer(formId);
            verify(answerService, times(1)).GetAllReviewers(formId);
        }

        @Test
        @WithMockUser(username = "user", password = "user", roles = "USER")
        public void testGetCategoryAverages() throws Exception {
            //Given: A user is logged in
            //When: Accessing "/account/averages/{id}" to view their averages
            //Then: The response status is OK, the view is "account/userGraphs",
            // the model attributes are added correctly
            //and the graphService methods were called with the correct parameters


            // Sample data for formId
            String formId = "formId";

            // Sample data for category averages
            List<Map<String, Object>> categoryAverages = new ArrayList<>();
            Map<String, Object> category1 = new HashMap<>();
            category1.put("category", "Category1");
            category1.put("average", 4.5);
            categoryAverages.add(category1);

            // Sample data for form text answers
            Map<String, List<String>> formTextAnswer = new HashMap<>();
            List<String> answers1 = Arrays.asList("Answer1", "Answer2", "Answer3");
            formTextAnswer.put("Question1", answers1);

            // Sample data for chart data
            List<Map<String, Object>> chartData = new ArrayList<>();
            Map<String, Object> chartDataMap1 = new HashMap<>();
            chartDataMap1.put("category", "Category1");
            chartDataMap1.put("average", 4.5);
            chartData.add(chartDataMap1);

            // Sample data for relationship counts
            List<Map<String, Object>> relationshipCounts = new ArrayList<>();
            Map<String, Object> relationshipCount1 = new HashMap<>();
            relationshipCount1.put("relationship", "Relation1");
            relationshipCount1.put("count", 10);
            relationshipCounts.add(relationshipCount1);

            Model model = mock(Model.class);

            // Mocking behavior of graphService methods
            when(graphService.getCategoryAverages(formId)).thenReturn(categoryAverages);
            when(graphService.getFormTextAnswer(formId)).thenReturn(formTextAnswer);
            when(graphService.getChartData(formId)).thenReturn(chartData);
            when(graphService.getRelationshipCounts()).thenReturn(relationshipCounts);

            // Call the controller method
            String view = graphController.getCategoryAverages(formId, model);

            // Verify that the controller method returns the expected view name
            assertEquals("account/userGraphs", view);

            // Verify that the model attributes are added correctly
            verify(model, times(1)).addAttribute("categoryAverages", categoryAverages);
            verify(model, times(1)).addAttribute("formTextAnswer", formTextAnswer);
            verify(model, times(1)).addAttribute("chartData", chartData);
            verify(model, times(1)).addAttribute("relationshipCounts", relationshipCounts);

            // Verify that graphService methods were called with the correct parameters
            verify(graphService, times(1)).getCategoryAverages(formId);
            verify(graphService, times(1)).getFormTextAnswer(formId);
            verify(graphService, times(1)).getChartData(formId);
            verify(graphService, times(1)).getRelationshipCounts();
        }

}



