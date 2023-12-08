package uk.cf.ac.LegalandGeneralTeam11.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import uk.cf.ac.LegalandGeneralTeam11.Form.FormServiceImpl;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestService;

@Controller

public class userAccountLinks {
    FormRequestService formRequestService;


    FormServiceImpl formService;

    @Autowired
    public userAccountLinks(FormRequestService formRequestService, FormServiceImpl formservice) {
        this.formService = formservice;
        this.formRequestService = formRequestService;
    }

    @GetMapping("/my_forms")
    public String getMyForms() {
        return "account/allForms";
    }

}
