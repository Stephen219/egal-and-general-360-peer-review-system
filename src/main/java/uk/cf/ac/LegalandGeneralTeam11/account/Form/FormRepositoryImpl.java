package uk.cf.ac.LegalandGeneralTeam11.account.Form;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class FormRepositoryImpl {
    JdbcTemplate jdbcTemplate;
    /**
     * Default constructor
     */

    public FormRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveFormRequest(FormRequest formRequest) {

        // TODO implement here
    }


    public List<FormRequest> getAllFormRequests() {
        // TODO implement here
        return null;
    }


    public FormRequest getFormRequestById(Long formId) {
        // TODO implement here
        return null;
    }


    public List<FormRequest> getAllByUser(Long userId) {
        // TODO implement here
        return null;
    }
}
