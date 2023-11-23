package uk.cf.ac.LegalandGeneralTeam11.account.Form;

import java.util.List;

public interface FormService {
    /**
     * Save a form request.
     *
     * @param formRequest The form request to be saved.
     */
    void saveFormRequest(FormRequest formRequest);

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










}
