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

    public Long getTheNumberOfResponsesForAform(String formId) {
        return formRepository.getTheNumberOfResponsesForAform(formId);
    }
}
