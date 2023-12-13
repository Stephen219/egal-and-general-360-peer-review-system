package uk.cf.ac.LegalandGeneralTeam11.answers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository

public class AnswerRepo {

    JdbcTemplate jdbcTemplate;

    private RowMapper<Answer> answerMapper;

    public AnswerRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        setAnswerMapper();

    }



    private void setAnswerMapper() {
        answerMapper = (rs, i) -> new Answer(
                rs.getLong("id"),
                rs.getString("answer"),
                rs.getLong("questionId"),
                rs.getString("formId"),
                rs.getString("username")
        );

    }

    public void saveAnswer(Answer answer) {
        String sql = "INSERT INTO answers (answer, question_Id, form_Id, responder) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, answer.getAnswer(), answer.getQuestionId(), answer.getFormId(), answer.getUsername());
    }


    public List<FormAnswerObject> getFormAnswers(String formId) {
        String sql = "SELECT q.category, q.question, a.answer, a.responder " +
                "FROM questions q " +
                "JOIN answers a ON q.id = a.question_id " +
                "WHERE a.form_id = ?";

        return jdbcTemplate.query(sql, new Object[]{formId}, (rs, rowNum) -> {
            String category = rs.getString("category");
            String question = rs.getString("question");
            String answer = rs.getString("answer");
            String responder = rs.getString("responder");

            return new FormAnswerObject(category, question, answer, responder);
        });
    }

    public Map<String, Double> getCategoryAverages(String formId) {
        String sql = "SELECT q.category, AVG(CAST(a.answer AS DECIMAL)) AS average " +
                "FROM questions q " +
                "JOIN answers a ON q.id = a.question_id " +
                "WHERE a.form_id = ? " +
                "GROUP BY q.category";

        return jdbcTemplate.query(sql, new Object[]{formId}, (rs, rowNum) -> {
            String category = rs.getString("category");
            Double average = rs.getDouble("average");

            Map<String, Double> categoryAverage = new HashMap<>();
            categoryAverage.put(category, average);

            return categoryAverage;
        }).stream().reduce(new HashMap<>(), (acc, m) -> {
            acc.putAll(m);
            return acc;
        });
    }


    /**
     * this is the method that gets the answers for a question for a form for a user and returns them in a map
     * @param responder
     * @param formId
     * @return
     */






    public Map<String, String> getFormAnswersForaUser(String responder, String formId) {
        String sql = "SELECT q.question, " +
                "CASE " +
                "    WHEN a.answer = 1 THEN 'Never' " +
                "    WHEN a.answer = 2 THEN 'Rarely' " +
                "    WHEN a.answer = 3 THEN 'Sometimes' " +
                "    WHEN a.answer = 4 THEN 'Often' " +
                "    WHEN a.answer = 5 THEN 'Always' " +
                "    ELSE a.answer " +
                "END AS descriptive_answer " +
                "FROM questions q " +
                "JOIN answers a ON q.Id = a.question_id " +
                "WHERE a.responder = ? AND a.form_id = ?";

        try {
            Map<String, String> result = new HashMap<>();

            jdbcTemplate.query(sql, (rs) -> {
                String question = rs.getString("question");
                String descriptiveAnswer = rs.getString("descriptive_answer");
                result.put(question, descriptiveAnswer);
            }, responder, formId);

            return result;
        } catch (Exception e) {

            e.printStackTrace();
            return new HashMap<>();
        }
    }





    public Map<String, List<String>> getQuestionAnswersUser(String responder, String formId) {
        String sql = "SELECT " +
                "CASE " +
                "    WHEN q.category = 'textarea' THEN 'open ended' " +
                "    ELSE q.category " +
                "END AS category, " +
                "q.question, " +
                "CASE " +
                "    WHEN a.answer = 1 THEN 'Never' " +
                "    WHEN a.answer = 2 THEN 'Rarely' " +
                "    WHEN a.answer = 3 THEN 'Sometimes' " +
                "    WHEN a.answer = 4 THEN 'Often' " +
                "    WHEN a.answer = 5 THEN 'Always' " +
                "    ELSE a.answer " +
                "END AS descriptive_answer " +
                "FROM questions q " +
                "JOIN answers a ON q.Id = a.question_id " +
                "WHERE a.responder = ? AND a.form_id = ? " +
                "ORDER BY q.category, q.question";

        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, responder, formId);

            // Grouping by category
            Map<String, List<String>> resultMap = new HashMap<>();
            String currentCategory = null;
            List<String> currentList = null;

            for (Map<String, Object> row : rows) {
                String category = (String) row.get("category");
                String descriptiveAnswer = (String) row.get("descriptive_answer");

                if (!category.equals(currentCategory)) {
                    // New category encountered
                    if (currentList != null) {
                        resultMap.put(currentCategory, currentList);
                    }

                    currentCategory = category;
                    currentList = new ArrayList<>();
                }

                currentList.add(descriptiveAnswer);
            }

            // Adding the last category
            if (currentList != null) {
                resultMap.put(currentCategory, currentList);
            }

            return resultMap;
        } catch (Exception e) {

            e.printStackTrace();
            return new HashMap<>();
        }
    }
<<<<<<< HEAD
=======

    public List<String> GetAllReviewers(String formId) {
        String sql = "SELECT DISTINCT responder FROM answers WHERE form_id = ?";
        return jdbcTemplate.queryForList(sql, String.class, formId);
    }





//public Map<String, List<String>> getQuestionAnswersUser(String responder, String formId) {
//    String sql = "SELECT " +
//            "  CASE " +
//            "    WHEN q.category = 'textarea' THEN 'open ended ' " +
//            "    ELSE q.category " +
//            "  END AS category, " +
//            "  q.question, " +
//            "  CASE " +
//            "    WHEN a.answer = 1 THEN 'Never' " +
//            "    WHEN a.answer = 2 THEN 'Rarely' " +
//            "    WHEN a.answer = 3 THEN 'Sometimes' " +
//            "    WHEN a.answer = 4 THEN 'Often' " +
//            "    WHEN a.answer = 5 THEN 'Always' " +
//            "    ELSE NULL " +
//            "  END AS descriptive_answer " +
//            "FROM " +
//            "  questions q " +
//            "JOIN " +
//            "  answers a ON q.Id = a.question_id " +
//            "WHERE " +
//            "  a.responder = ? AND a.form_id = ? " +
//            "GROUP BY q.category, q.question";
//
//    try {
//        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, responder, formId);
//
//        Map<String, List<String>> result = new HashMap<>();
//
//        for (Map<String, Object> row : rows) {
//            String category = (String) row.get("category");
//            String question = (String) row.get("question");
//            String descriptiveAnswer = (String) row.get("descriptive_answer");
//
//            // Check if the category is already in the result map
//            if (!result.containsKey(category)) {
//                result.put(category, new ArrayList<>());
//            }
//
//            // Add the descriptive answer to the list under the category
//            result.get(category).add(question + ": " + descriptiveAnswer);
//        }
//
//        return result;
//    } catch (Exception e) {
//        // Handle any errors here
//        e.printStackTrace();
//        return new HashMap<>(); // Return an empty map or handle the error accordingly
//    }
//}
>>>>>>> 75ff2f25e349ab6a367c7cb178159ce4bf825e40



}
