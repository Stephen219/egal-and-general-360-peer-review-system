package uk.cf.ac.LegalandGeneralTeam11.account.FormRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller

public class FormRequestController {
    private final FormRequestService formRequestService;

    @Autowired
    public FormRequestController(FormRequestService aformRequestService) {
        this.formRequestService = aformRequestService;
    }


    @GetMapping("form/new")
    public ModelAndView getForm() {
        System.out.println(formRequestService);
        System.out.println("hello");
        long formId = 1;
        long userId = 1;
        String formName = "test";
        String approvalStatus = "pending";
        FormRequest formRequest = new FormRequest(formId, userId, formName, approvalStatus);
        System.out.println(formRequest);
        formRequestService.saveFormRequest(formRequest);
        ModelAndView modelAndView = new ModelAndView("redirect:/account");
        return modelAndView;




    }



}
