package uk.cf.ac.LegalandGeneralTeam11.Form;

import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequest;

import java.time.LocalDate;
import java.util.List;

public interface FormService {

    public void grantFormAccess(String username, LocalDate formDate);
    public String getUsernameForFormRequest(Long formRequestId);

    public void updateFormRequest(FormRequest formRequest);

    public List<Form> getFormByUser(String username);
    public List<String> getUsers();

    public Form getFormById(String id);
    public void addFormReviewers(String FormId, List<String> reviewers);

    public void updateReviewersAfterSubmission(String FormId, String reviewer, String relationship);

    public Boolean getIfHasFilledForm(String formId, String email);
    public List<String> getReviewersForAForm(String formId);

    public Boolean ifUserHasSelfReviewed(String formId, String email);

    public List<Form> getAllForms();
    public List<Form> getFormsByStatus(String status);

    public Long getTheNumberOfResponsesForAform(String formId);











}
