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
        // Assuming you have a method to retrieve the form request entity by ID
        FormRequest formRequest = FormRequestRepository.findFormRequestById(formRequestId);


        // Assuming the form request entity has a user association, adjust this according to your data model
        String username = formRequest.getUsername();

        // Assuming the User entity has a method to get the username, adjust this according to your data model
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

}
