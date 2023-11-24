package uk.cf.ac.LegalandGeneralTeam11.account.FormRequest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public class FormRequestRepoImpl implements FormRequestRepository{

    JdbcTemplate jdbcTemplate;
    /**
     * Default constructor
     */

    public FormRequestRepoImpl(JdbcTemplate jdbcTemplate) {
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
