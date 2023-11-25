package uk.cf.ac.LegalandGeneralTeam11.FormRequest;

import java.util.List;

public interface FormRequestService {
    /**
     * Save a form request.
     *
     * @param formRequest The form request to be saved.
     */
//    void saveFormRequest(FormRequest formRequest);

    /**
     * Get a list of all form requests.
     *
     * @return List of form requests.
     */
    List<FormRequest> getAllFormRequests();
    /**
     * Get a form request by its ID.
     *
     * @param formId The ID of the form request.
     * @return The form request with the specified ID, or null if not found.
     */
    FormRequest getFormRequestById(Long formId);

    /**
     * Get a list of all form requests.
     * @param userId The ID of the user.
     *
     * @return List of form requests for a specific user.
     */
    List<FormRequest> getAllByUser(Long userId);
    /**
     * Create a form request.
     *
     * @param formRequest The form request to be created.
     */


    void createFormRequest(FormRequest formRequest);
    /**
     * finding pending requests by username
     * @param username The username of the user.
     */
    public List<FormRequest> findPendingRequestsByUsername(String username);

}
