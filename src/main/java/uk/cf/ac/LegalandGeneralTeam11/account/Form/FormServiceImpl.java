package uk.cf.ac.LegalandGeneralTeam11.account.Form;

import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class FormServiceImpl implements FormRequestService {
    private FormRequestRepository formRequestRepository ;

    /**
     * Constructor for FormServiceImpl
     * @param formRequestRepository
     */

    public FormServiceImpl(FormRequestRepository formRequestRepository) {
        this.formRequestRepository = formRequestRepository;
    }

    /**
     * Save a form request.
     *
     * @param formRequest The form request to be saved.
     */
    public void saveFormRequest(FormRequest formRequest) {
        formRequestRepository.saveFormRequest(formRequest);

    }

    /**
     * Get a list of all form requests.
     *
     * @return List of form requests.
     */

    public List<FormRequest> getAllFormRequests() {
        return formRequestRepository.getAllFormRequests();

    }

    /**
     * Get a form request by its ID.
     *
     * @param formId The ID of the form request.
     * @return The form request with the specified ID, or null if not found.
     */

    public FormRequest getFormRequestById(Long formId) {
        return formRequestRepository.getFormRequestById(formId);
    }

    /**
     * Get a list of all form requests.
     * @param userId The ID of the user.
     *
     * @return List of form requests for a specific user.
     */


    public List<FormRequest> getAllByUser(Long userId) {
        return formRequestRepository.getAllByUser(userId);}
    public void createFormRequest(FormRequest formRequest) {
        System.out.println("i have created a form request");

    }














}
