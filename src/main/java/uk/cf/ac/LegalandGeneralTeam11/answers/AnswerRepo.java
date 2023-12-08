package uk.cf.ac.LegalandGeneralTeam11.answers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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





}
