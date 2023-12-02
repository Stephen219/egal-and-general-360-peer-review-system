package uk.cf.ac.LegalandGeneralTeam11.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.cf.ac.LegalandGeneralTeam11.Form.Form;
import uk.cf.ac.LegalandGeneralTeam11.Form.FormServiceImpl;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequest;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestService;

import java.util.List;


@Controller

public class accountController {
    @Autowired
    FormRequestService formRequestService;

    @Autowired
    FormServiceImpl formservice;

    public accountController(FormRequestService formRequestService, FormServiceImpl formservice) {
        this.formservice = formservice;
        this.formRequestService = formRequestService;
    }


    @GetMapping("/account")
    public ModelAndView getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println(currentPrincipalName);
        String username = currentPrincipalName;

        //List<Form> forms = formservice.getFormByUser(username);
        ModelAndView modelAndView = new ModelAndView("account/dashboard");
        //modelAndView.addObject("forms", forms);

        return modelAndView;


    }
    @GetMapping("/admin")
    public ModelAndView home() {
        List<FormRequest> formRequests = formRequestService.getAllByStatus("Pending");
        ModelAndView modelAndView = new ModelAndView("account/adminDashboard");
        modelAndView.addObject("formRequests", formRequests);
        System.out.println(formRequests);

        return modelAndView;
    }
}
