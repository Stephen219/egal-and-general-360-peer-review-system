package uk.cf.ac.LegalandGeneralTeam11.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequest;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestService;

import java.util.List;


@Controller

public class accountController {
    @Autowired
    FormRequestService formRequestService;

    public accountController(FormRequestService formRequestService) {
        this.formRequestService = formRequestService;
    }


    @GetMapping("/account")
    public String getAccount() {
        return "account/dashboard";
    }
    @GetMapping("/admin")
    public ModelAndView home() {
        List<FormRequest> formRequests = formRequestService.getAllFormRequests();
        ModelAndView modelAndView = new ModelAndView("account/adminDashboard");
        modelAndView.addObject("formRequests", formRequests);
        System.out.println(formRequests);
        //return "testfile";

        return modelAndView;
    }
}
