package uk.cf.ac.LegalandGeneralTeam11.Form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequest;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestService;

import java.time.LocalDate;
import java.util.List;

@Controller

public class FormControllerImpl {
    @Autowired
    private FormServiceImpl formServiceImpl;
    @Autowired
    private FormRequestService FormRequestService;

    public FormControllerImpl(FormServiceImpl formServiceImpl) {
        this.formServiceImpl = formServiceImpl;
    }

//    @GetMapping("/accept{id}")
//    public String acceptForm(@PathVariable Long id) {
//        String username = "user";
//
//        formService.grantFormAccess(username, formDate);
//        return "accept";
//    }


    @GetMapping("/accept/{id}")
    public String acceptForm(@PathVariable Long id) {
        // Assuming you have a method to retrieve the username associated with the form request
        String username = formServiceImpl.getUsernameForFormRequest(id);
        System.out.println(username);
        System.out.println(id);

        FormRequest formRequest = FormRequestService.getFormRequestById(id);
        System.out.println(formRequest);
        formServiceImpl.updateFormRequest(formRequest);
        LocalDate formDate = LocalDate.now();

        // Grant form access for the user associated with the form request
        formServiceImpl.grantFormAccess(username, formDate);

        return "redirect:/admin";
    }
    // the below method is used o redirect the user to the form page and display the form
    // the form is displayed using the form id

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/review/{formId}")
    public ModelAndView getForm(@PathVariable String formId) {
        Form form = formServiceImpl.getFormById(formId);

        List<String> assesors = formServiceImpl.getUsers();
        ModelAndView modelAndView = new ModelAndView("fix");
        modelAndView.addObject("form", form);
        modelAndView.addObject("assessors", assesors);
        return modelAndView;
    }

// the below method is used to submit the form and redirect the user to the account page

    // it can be changed    but as per now i keep it


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/review/{formId}")
    public ModelAndView submitAssesors(@PathVariable String formId, @RequestParam("assessors") List<String> selectedAssessors) {
        System.out.println("Selected Assessors:");
        Form form = formServiceImpl.getFormById(formId);
        ModelAndView modelAndView = new ModelAndView("redirect:/account");

        modelAndView.addObject("form", form);
        for (String assessor : selectedAssessors) {
            System.out.println(assessor);
            System.out.println(formId);
        }
        return modelAndView;
    }


}
