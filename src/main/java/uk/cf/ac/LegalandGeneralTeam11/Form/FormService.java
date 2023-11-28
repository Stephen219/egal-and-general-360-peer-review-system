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










}
