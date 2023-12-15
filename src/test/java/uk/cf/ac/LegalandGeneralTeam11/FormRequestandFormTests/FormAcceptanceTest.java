package uk.cf.ac.LegalandGeneralTeam11.FormRequestTests;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import uk.cf.ac.LegalandGeneralTeam11.Form.Form;
import uk.cf.ac.LegalandGeneralTeam11.Form.FormService;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequest;

import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestService;


import java.time.LocalDate;

import java.util.*;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc

public class FormAcceptanceTest {

    @MockBean
    private FormRequestService formRequestService;
    @Autowired
    private FormService formService;

    @Test
    public void testAdminAcceptFormRequest() {
        String ownerUsername = "charlie";
        FormRequest formRequest = new FormRequest(10L, ownerUsername, new Date(), null, "pending");
        formRequestService.createFormRequest(formRequest);
        formService.grantFormAccess(formRequest.getUsername(), LocalDate.now());
        List<Form> forms = formService.getFormByUser(ownerUsername);
        assertTrue("A new form with the owner's username should be present.", formExistsForOwner(forms, ownerUsername));
        assertThat("A new form with the owner's username should be present.",
                forms, hasItem(hasProperty("username", equalTo(ownerUsername))));
    }

    private boolean formExistsForOwner(List<Form> forms, String ownerUsername) {
        for (Form form : forms) {
            if (ownerUsername.equals(form.getUsername())) {
                return true;
            }
        }
        return false;
    }
}











