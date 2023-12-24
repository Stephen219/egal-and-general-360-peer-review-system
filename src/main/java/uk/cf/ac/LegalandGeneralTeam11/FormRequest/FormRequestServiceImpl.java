package uk.cf.ac.LegalandGeneralTeam11.FormRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class FormRequestServiceImpl implements FormRequestService{
    private FormRequestRepository formRequestRepository;

    /**
     * Constructor for FormRequestServiceImpl
     * @param aFormRequestRepository
     */
    @Autowired

    public FormRequestServiceImpl(FormRequestRepository aFormRequestRepository) {
            this.formRequestRepository = aFormRequestRepository;
        }
        /**
         * Gets all form requests
         * @return List of form requests
         */

        public List<FormRequest> getAllFormRequests() {
            return formRequestRepository.getAllFormRequests();

        }
        /**
         * Gets a form request by its ID
         * @param formId The ID of the form request
         * @return The form request with the specified ID, or null if not found
         */

        public FormRequest getFormRequestById(Long formId) {
            FormRequest formRequest = formRequestRepository.getFormRequestById(formId);
            if (formRequest == null) {
                return null;
            }
            return formRequest;
        }
        /**
         * Gets all form requests for a specific user
         * @param username The ID of the user
         * @return List of form requests for a specific user
         */

        public List<FormRequest> getAllByUser(String username) {

            return formRequestRepository.getAllByUser(username);
        }
        /**
         * Creates a form request
         * @param formRequest The form request to be created
         */

        public void createFormRequest(FormRequest formRequest) {
            List<FormRequest> pendingRequests = formRequestRepository.findPendingRequestsByUsername(formRequest.getUsername());

            if (pendingRequests.isEmpty()) {
                formRequestRepository.createFormRequest(formRequest);
            } else {
                throw new IllegalStateException("Hang on, you already have a pending request!");
            }



        }
        /**
         * finding pending requests by username
         * @param username The username of the user
         */
        //this is to prevent the user from creating multiple requests while they have a pending request

        public List<FormRequest> findPendingRequestsByUsername(String username) {
            return formRequestRepository.findPendingRequestsByUsername(username);
        }
    @Override
    public List<FormRequest> getAllByStatus( String status){
        return formRequestRepository.getAllByStatus(status);
    }

    public void rejectFormRequest(FormRequest formRequest){
        formRequestRepository.rejectFormRequest(formRequest);
    }


}
