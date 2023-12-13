package uk.cf.ac.LegalandGeneralTeam11.Form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequest;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestRepository;

import java.time.LocalDate;
import java.util.List;

@Service

public class FormServiceImpl implements FormService {
    @Autowired
    private FormRequestRepository FormRequestRepository;
    @Autowired
    private FormRepositoryImpl formRepository;
    public void grantFormAccess(String username, LocalDate formDate) {
        Form form = new Form();
        form.setUsername(username);
        form.setFormDate(formDate);
        formRepository.addForm(username, formDate);



    }
    public String getUsernameForFormRequest(Long formRequestId) {
        FormRequest formRequest = FormRequestRepository.findFormRequestById(formRequestId);
        String username = formRequest.getUsername();
        return username;
    }


    public void updateFormRequest(FormRequest formRequest) {
        FormRequestRepository.updateFormRequest(formRequest);
    }

public List<Form> getFormByUser(String username) {
        return formRepository.getFormByUser(username);
    }

    public List<String> getUsers() {
        return formRepository.getUsers();
    }

    public Form getFormById(String id) {
        return formRepository.getFormById(id);
    }

    public void addFormReviewers(String FormId, List<String> reviewers) {
        formRepository.addFormReviewers(FormId, reviewers);
    }

    public void updateReviewersAfterSubmission(String FormId, String reviewer, String relationship) {
        formRepository.updateReviewersAfterSubmission(FormId, reviewer, relationship);
    }
    public List<String> getReviewersForAForm(String formId) {
        return formRepository.getReviewersForAForm(formId);
    }

    public Boolean getIfHasFilledForm(String formId, String email) {
        if (formRepository.getIfHasFilledForm(formId, email) == true){
            return true;
        }

        return false;
    }

    public Boolean ifUserHasSelfReviewed(String formId, String email) {
        return formRepository.ifUserHasSelfReviewed(formId, email);
    }

    public List<Form> getAllForms() {
        return formRepository.getAllForms();
    }

    public List<Form> getFormsByStatus(String status) {
        return  formRepository.getFormsByStatus(status);
    }
    /**
     * get the number of responses for a form
     * @param formId the id of the form
     * @return the number of responses
     */

    public Long getTheNumberOfResponsesForAform(String formId) {
        return formRepository.getTheNumberOfResponsesForAform(formId);
    }
    // TODO: implement this method so as the user can see the forms assigned to them this is after doing the user object


    /**
     * get all forms assigned to a user
     * @param email the email of the user
     * @return a list of forms
     */

    public List<Form> getFormsAssignedToUser(String email) {
        return formRepository.getFormsAssignedToUser(email);
    }


    public Boolean checkFormCompleted(String formId){
        return formRepository.checkFormCompleted(formId) || formRepository.checkFormCompletedByStatus(formId);
    }

    public void updateFormStatus(String formId, String status) {
        formRepository.updateFormStatus(formId, status);
    }


    public String getFormOwner(String formId) {
        return formRepository.getFormOwner(formId);
    }


}
