package uk.cf.ac.LegalandGeneralTeam11.answers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
}
