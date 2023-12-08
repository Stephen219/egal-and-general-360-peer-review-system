package uk.cf.ac.LegalandGeneralTeam11.FormRequest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository

public class FormRequestRepoImpl implements FormRequestRepository{

    JdbcTemplate jdbcTemplate;
    private RowMapper<FormRequest> FormRequestMapper;
    /**
     * Default constructor
     */

    public FormRequestRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        setFormRequestMapper();
    }
    /**
     * Set the mapper for the FormRequest
     */


    private void setFormRequestMapper() {

        FormRequestMapper = (rs, i) -> new FormRequest(
                rs.getLong("Id"),
                rs.getString("username"),
                rs.getDate("created_at"),
                rs.getDate("updated_at"),
                rs.getString("approval_status")
        );




    }
    /**
     * Get all form requests
     * @return list of form requests
     */

    public List<FormRequest> getAllFormRequests() {
        String sql = "SELECT * FROM form_requests";
        return jdbcTemplate.query(sql, FormRequestMapper);
    }
    /**
     * Get form requests by id
     * @param Id
     * @return form request with the given id
     */


    public FormRequest getFormRequestById(Long Id) {
        String sql = "SELECT * FROM form_requests WHERE Id = ?";
        return jdbcTemplate.queryForObject(sql, FormRequestMapper, Id);
    }
    /**
     * Get all form requests by user
     * @param userId
     */
    //TODO: change to username


    public List<FormRequest> getAllByUser(Long userId) {
        String sql = "SELECT * FROM form_requests WHERE username = ?";
        return jdbcTemplate.query(sql, FormRequestMapper, userId);

    }
    /**
     * Create a form request
     * @param formRequest to be created
     */

    public void createFormRequest(FormRequest formRequest) {
        String sql = "INSERT INTO form_requests (username, created_at, updated_at, approval_status) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                formRequest.getUsername(),
                formRequest.getRequestDate(),
                formRequest.getUpdateDate(),
                formRequest.getApprovalStatus());
    }

    /**
     * get all form requests by status
     * @param status
     * @return list of form requests
     */
    public List<FormRequest> getAllByStatus(String status) {
        String sql = "SELECT * FROM form_requests WHERE approval_status = ?";
        return jdbcTemplate.query(sql, FormRequestMapper, status);
    }


    /**
     * get all formrequests by status and user
     * @param status
     * @param userId
     * @return list of form requests
     *
     *
     */

    public List<FormRequest> getAllByStatusAndUser(String status, String userId) {
        String sql = "SELECT * FROM form_requests WHERE approval_status = ? AND username = ?";
        return jdbcTemplate.query(sql, FormRequestMapper, status, userId);
    }

    /**
     * get all form requests by user
     * @param username#
     * @return list of form requests
     */
    //while i use username as  a param,  i assume its a unique identifier for the user

    public List<FormRequest> findPendingRequestsByUsername(String username) {
        String sql = "SELECT * FROM form_requests WHERE username = ? AND approval_status = 'PENDING'";
        return jdbcTemplate.query(sql, FormRequestMapper, username);
    }


    /**
     * getting a form request by its id
     * @param formRequestId
     * @return
     */
    public FormRequest findFormRequestById(Long formRequestId) {
        String sql = "SELECT * FROM form_requests WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, FormRequestMapper, formRequestId);}


    /**
     *  updating a form request
     */

    public void updateFormRequest(FormRequest formRequest) {
        String sql = "UPDATE form_requests SET approval_status = 'APPROVED', updated_at = ? WHERE id = ?";

        jdbcTemplate.update(
                sql,
                LocalDateTime.now(),
                formRequest.getId()
        );
    }







}
