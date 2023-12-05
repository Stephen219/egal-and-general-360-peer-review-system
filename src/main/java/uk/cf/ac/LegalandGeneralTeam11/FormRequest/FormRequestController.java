package uk.cf.ac.LegalandGeneralTeam11.FormRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class FormRequestController {
    private final FormRequestService formRequestService;

    @Autowired
    public FormRequestController(FormRequestService aformRequestService) {
        this.formRequestService = aformRequestService;
    }



    @GetMapping("form/new")
    public ModelAndView addFormRequest(RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            FormRequest formRequest = new FormRequest(username);
            System.out.println(formRequest);
            formRequestService.createFormRequest(formRequest);
            //TODO: the email sending should be done here by calling the method.
            //TODO: currently, the form request is created using the username, but the form request should be created using the user id thoough not necessary
            redirectAttributes.addFlashAttribute("flashMessage", "your form request has been submited!");
            ModelAndView modelAndView = new ModelAndView("redirect:/account");
            return modelAndView;
        }
        catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("flashError", e.getMessage());
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("flashError", "An error occurred while creating the form request");
        }


        return new ModelAndView("redirect:/account");

    }

}
