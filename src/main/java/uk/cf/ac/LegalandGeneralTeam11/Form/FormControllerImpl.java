package uk.cf.ac.LegalandGeneralTeam11.Form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequest;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestService;
import uk.cf.ac.LegalandGeneralTeam11.SelfAssessment.SelfAssessService;
import uk.cf.ac.LegalandGeneralTeam11.SelfAssessment.SelfAssessment;

import java.time.LocalDate;
import java.util.List;

@Controller

public class FormControllerImpl {
    @Autowired
    private FormService formService;
    @Autowired
    private FormRequestService FormRequestService;
    @Autowired
    SelfAssessService selfAssessService;

    public FormControllerImpl(FormServiceImpl formServiceImpl) {
        this.formService = formServiceImpl;
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
        String username = formService.getUsernameForFormRequest(id);
        System.out.println(username);
        System.out.println(id);

        FormRequest formRequest = FormRequestService.getFormRequestById(id);
        System.out.println(formRequest);
        formService.updateFormRequest(formRequest);
        LocalDate formDate = LocalDate.now();
        formService.grantFormAccess(username, formDate);

        return "redirect:/admin";
    }
    // the below method is used o redirect the user to the form page and display the form
    // the form is displayed using the form id

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/review/{formId}")
    public ModelAndView getForm(@PathVariable String formId) {
        Form form = formService.getFormById(formId);
        SelfAssessment list = new SelfAssessment("", "" );


        List<String> assesors = formService.getUsers(); // we will deal  with the group of assesors later
        ModelAndView modelAndView = new ModelAndView("forms/360form");
        modelAndView.addObject("reviewForm", list);
        modelAndView.addObject("form", form);
        modelAndView.
        return modelAndView;
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/review/{formId}")
    public String submitReview(@PathVariable String formId, @ModelAttribute SelfAssessment reviewForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Form form = formService.getFormById(formId);
        System.out.println(formId);

        ModelAndView modelAndView = new ModelAndView("redirect:/account");
        modelAndView.addObject("form", form);
        modelAndView.addObject("list", reviewForm);
        reviewForm.setResponder(username);
        reviewForm.setFormId(formId);
        selfAssessService.saveSelfAssessment(reviewForm);


        System.out.println(reviewForm);
        System.out.println("succes inndcncfjndbnef");



        return "redirect:/account";}





}
