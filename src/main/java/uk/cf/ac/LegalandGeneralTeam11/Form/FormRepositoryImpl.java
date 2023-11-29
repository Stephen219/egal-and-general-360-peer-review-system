package uk.cf.ac.LegalandGeneralTeam11.Form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
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




}
