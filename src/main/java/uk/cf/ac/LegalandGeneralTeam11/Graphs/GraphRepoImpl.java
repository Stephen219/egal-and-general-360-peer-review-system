package uk.cf.ac.LegalandGeneralTeam11.Graphs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GraphRepoImpl implements GraphRepo{

    JdbcTemplate jdbcTemplate;
    @Autowired
    public GraphRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Gets the average score for a specific category
     * @param formid The id of the form
     * @param category The category of the form
     * @return The average score for a specific category
     */
    public Float getAverageScore(String formid, String category) {
        String sql = "SELECT AVG(CAST(answers.answer AS DECIMAL)) " +
                "FROM answers " +
                "JOIN questions ON answers.question_id = questions.Id " +
                "JOIN 360forms ON answers.form_id = 360forms.Id " +
                "WHERE answers.form_id = ? AND questions.category <> textarea AND questions.category = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{formid, category}, Float.class);
    }
    public String getFormCategory(String formid) {
        String sql = "SELECT category FROM questions WHERE form_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{formid}, String.class);
    }
    public List<Map<String, Object>> getCategoryAverages(String formid) {
        String sql = "SELECT q.category, AVG(a.answer) AS average " +
                "FROM questions q " +
                "JOIN answers a ON q.id = a.question_id " +
                "WHERE q.category <> 'textarea' AND a.form_id = ? " +
                "GROUP BY q.category";

        return jdbcTemplate.queryForList(sql, formid); // Passing 'formid' as a parameter
    }
    public Map<String, List<String>> getFormTextAnswer(String formid) {
        String sql = "SELECT q.question, a.answer " +
                "FROM questions q " +
                "JOIN answers a ON q.id = a.question_id " +
                "WHERE q.category = 'textarea' AND a.form_id = ?";

        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, formid);

        Map<String, List<String>> textAnswersMap = new HashMap<>();

        for (Map<String, Object> row : resultList) {
            String question = (String) row.get("question");
            String answer = (String) row.get("answer");

            // Check if the question is already in the map
            if (textAnswersMap.containsKey(question)) {
                // If the question exists, add the answer to its list
                textAnswersMap.get(question).add(answer);
            } else {
                // If the question doesn't exist, create a new list with the answer
                List<String> answersList = new ArrayList<>();
                answersList.add(answer);
                textAnswersMap.put(question, answersList);
            }
        }

        return textAnswersMap;
    }

}

