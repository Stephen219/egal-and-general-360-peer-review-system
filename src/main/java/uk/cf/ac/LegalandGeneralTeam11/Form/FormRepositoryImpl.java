package uk.cf.ac.LegalandGeneralTeam11.Form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@Repository

public class FormRepositoryImpl implements FormRepoInterface {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private RowMapper<Form> FormMapper;
    /**
     * Default constructor
     */

    public FormRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        setFormMapper();
    }
    /**
     * Set the mapper for the FormRequest
     */


    private void setFormMapper() {
        // FormMapper is a lambda expression that takes a ResultSet (rs) and an index (i) as parameters
        FormMapper = (rs, i) -> new Form(
                rs.getString("id"),
                rs.getString("username"),
                rs.getDate("created_at").toLocalDate(),
                rs.getString("progress_status"));
    }


    /**
     * add a form to the database
     * @param username
     * @param formDate
     */

    public void addForm(String username, LocalDate formDate) {
        String id = UUID.randomUUID().toString();
        String sql = "INSERT INTO 360forms (id, username, created_at) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,id, username, formDate);
    }

    /**
     * get all forms by user
     * @param username the username of the user
     * @return a list of forms
     */

    public List<Form> getFormByUser(String username) {
        String sql = "SELECT * FROM 360forms WHERE username = ?";
        return (jdbcTemplate.query(sql, FormMapper, username));
    }

    /**
     * get all users
     * TODO   DELETE THIS        DELETED because it will be in the users repository
     * @return
     */

    // TOBE DELETED because it will be in the users repository
    public List<String> getUsers() {
        String sql = "SELECT username FROM users";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("username"));
    }
    /**
     * get a form by id
     * @param id the id of the form
     * @return the form
     */

    public Form getFormById(String id) {
        String sql = "SELECT * FROM 360forms WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, FormMapper, id);
    }


    /**
     * add reviewers to a form
     * @param FormId the id of the form
     * @param reviewers the reviewers
     */

    public void addFormReviewers(String FormId, List<String> reviewers) {
        String sql = "INSERT INTO reviewers (form_id, email) VALUES (?, ?)";
        for (String reviewer : reviewers) {
            jdbcTemplate.update(sql, FormId, reviewer);
        }
    }

    @Override
    public void updateReviewersAfterSubmission(String FormId, String reviewer, String relationship) {
        String sql = "UPDATE reviewers SET relationship = ?, hasFilledForm = true WHERE form_id = ? AND email = ?";
        jdbcTemplate.update(sql, relationship, FormId, reviewer);
    }

    /**
     * get all reviewers for a form
     * @param formId the id of the form
     * @return a list of reviewers
     */
    public List<String> getReviewersForAForm(String formId) {
        String sql = "SELECT email FROM reviewers WHERE form_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("email"), formId);
    }

    public Boolean getIfHasFilledForm(String formId, String email) {
        String sql = "SELECT hasFilledForm FROM reviewers WHERE form_id = ? AND email = ?";
        Boolean hasFilledForm = jdbcTemplate.queryForObject(sql, Boolean.class, formId, email);

        return hasFilledForm;
    }
    public Boolean ifUserHasSelfReviewed(String formId, String email) {
        try {
            String sql = "SELECT COUNT(DISTINCT responder) FROM answers WHERE form_id = ? AND responder = ?";
            int count = jdbcTemplate.queryForObject(sql, Integer.class, formId, email);
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Form> getAllForms() {
        String sql = "SELECT * FROM 360forms";
        return jdbcTemplate.query(sql, FormMapper);
    }

    public List<Form> getFormsByStatus(String status) {
        String sql = "SELECT * FROM 360forms WHERE progress_status = ?";
        return  jdbcTemplate.query(sql, FormMapper, status);
    }

    public Long getTheNumberOfResponsesForAform(String formId) {
        String sql = "SELECT COUNT(DISTINCT responder) FROM answers WHERE form_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, formId);
    }

    public List<Form> getFormsAssignedToUser(String email) {

      String sql =   "SELECT f.*\n" +
                "FROM 360forms f\n" +
                "JOIN reviewers r ON f.id = r.form_id\n" +
                "WHERE r.email = ? AND r.hasFilledForm = false;\n";
       // String sql = "SELECT f.* FROM 360forms f JOIN reviewers r ON f.id = r.form_id WHERE r.email = ?";

        try {
            return jdbcTemplate.query(sql, preparedStatement -> {
                preparedStatement.setString(1, email);
            }, FormMapper);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void updateFormStatus(String formId, String status) {
        String sql = "UPDATE 360forms SET progress_status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, formId);
    }


//    public Boolean checkFormCompleted(String formId) {
//        String sql = "SELECT COUNT(DISTINCT responder) FROM answers " +
//                "JOIN reviewers r ON answers.form_id = r.form_id " +
//                "WHERE form_id = ? AND hasFilledForm = false";
//
//        int count = jdbcTemplate.queryForObject(sql, Integer.class, formId);
//
//        if (count > 0) {
//            throw new IllegalArgumentException("The form is no longer receiving responses.");
//        }
//
//        return true;
//    }


    public Boolean checkFormCompleted(String formId) {
        String sql = "SELECT COUNT(DISTINCT r.email) AS count_reviewers, " +
                "COUNT(DISTINCT CASE WHEN a.responder IS NOT NULL THEN r.email END) AS count_matched_emails " +
                "FROM reviewers r LEFT JOIN answers a ON r.email = a.responder " +
                "WHERE r.form_id = ?";

        Map<String, Object> result = jdbcTemplate.queryForMap(sql, formId);

        long countReviewers = (long) result.get("count_reviewers");
        long countMatchedEmails = (long) result.get("count_matched_emails");

        return countReviewers > 0 && countReviewers == countMatchedEmails;
    }



    public boolean checkFormCompletedByStatus(String formId) {
        String sql = "SELECT progress_status FROM 360forms WHERE id = ?";
        String progressStatus = jdbcTemplate.queryForObject(sql, String.class, formId);
        return "completed".equalsIgnoreCase(progressStatus);
    }
    public String getFormOwner(String formId) {
        String sql = "SELECT username FROM 360forms WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, formId);
    }









}
