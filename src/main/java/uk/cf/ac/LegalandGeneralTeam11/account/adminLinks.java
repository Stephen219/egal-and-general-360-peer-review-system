package uk.cf.ac.LegalandGeneralTeam11.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.cf.ac.LegalandGeneralTeam11.Form.FormServiceImpl;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestService;

@Controller


public class adminLinks {
    FormRequestService formRequestService;


    FormServiceImpl formService;

    @Autowired
    public adminLinks(FormRequestService formRequestService, FormServiceImpl formservice) {
        this.formService = formservice;
        this.formRequestService = formRequestService;
    }

    @GetMapping("/all_forms")
    public ModelAndView getAllForms() {
        ModelAndView modelAndView = new ModelAndView("account/allForms");

        modelAndView.addObject("forms", formService.getAllForms());
        return modelAndView;
    }

}
