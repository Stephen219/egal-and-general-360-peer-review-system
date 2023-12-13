package uk.cf.ac.LegalandGeneralTeam11.Form;

import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequest;

import java.time.LocalDate;
import java.util.List;

public interface FormRepoInterface {
    public void addForm(String username, LocalDate formDate);
    public List<Form> getFormByUser(String username);

    public List<String> getUsers();

    public Form getFormById(String id);
    public void addFormReviewers(String FormId, List<String> reviewers);

    public void updateReviewersAfterSubmission(String FormId, String reviewer, String relationship);
    public Boolean ifUserHasSelfReviewed(String formId, String email);

    public List<Form> getAllForms();
    public List<Form> getFormsByStatus(String status);

    public Long getTheNumberOfResponsesForAform(String formId);

    public List<Form> getFormsAssignedToUser(String email);
    public Boolean checkFormCompleted(String formId);

    public void updateFormStatus(String formId, String status);

    public boolean checkFormCompletedByStatus(String formId);

    public String getFormOwner(String formId);




}
