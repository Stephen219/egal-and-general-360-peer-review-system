package uk.cf.ac.LegalandGeneralTeam11.Form;

import java.time.LocalDate;
import java.util.List;

public interface FormRepoInterface {
    public void addForm(String username, LocalDate formDate);
    public List<Form> getFormByUser(String username);

    public List<String> getUsers();

    public Form getFormById(String id);
    public void addFormReviewers(String FormId, List<String> reviewers);


}
