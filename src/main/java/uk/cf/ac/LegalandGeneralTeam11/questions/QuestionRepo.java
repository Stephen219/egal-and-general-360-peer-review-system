package uk.cf.ac.LegalandGeneralTeam11.questions;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class QuestionRepo implements QuestionRepoInter{
    JdbcTemplate jdbcTemplate;
    private RowMapper<Question> QuestionMapper;


    public QuestionRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        setQuestionMapper();
    }

    private void setQuestionMapper() {
        QuestionMapper = (rs, i) -> new Question(
                rs.getLong("Id"),
                rs.getString("question"),
                rs.getString("category")
        );

    }

    public List<Question> getAllQuestions() {
        String sql = "SELECT * FROM questions WHERE category <> 'textarea'\n";
        return jdbcTemplate.query(sql, QuestionMapper);
    }

    public List<Question> getTextAreaQuestions() {
        String sql = "SELECT * FROM questions WHERE category = 'textarea'\n";
        return jdbcTemplate.query(sql, QuestionMapper);
    }






}
