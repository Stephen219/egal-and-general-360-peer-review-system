package uk.cf.ac.LegalandGeneralTeam11.Form;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequest;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestService;
import uk.cf.ac.LegalandGeneralTeam11.Graphs.GraphService;
import uk.cf.ac.LegalandGeneralTeam11.answers.Answer;
import uk.cf.ac.LegalandGeneralTeam11.answers.AnswerServiceInter;
import uk.cf.ac.LegalandGeneralTeam11.emails.EmailServiceImpl;
import uk.cf.ac.LegalandGeneralTeam11.questions.Question;
import uk.cf.ac.LegalandGeneralTeam11.questions.QuestionServiceInter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller

public class FormControllerImpl {
    @Autowired
    private FormService formService;
    @Autowired
    private FormRequestService FormRequestService;
    @Autowired
    private QuestionServiceInter questionServiceInter;
    @Autowired
    private AnswerServiceInter AnswerServiceInter;
    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    GraphService graphservic;

    public FormControllerImpl(FormServiceImpl formServiceImpl) {
        this.formService = formServiceImpl;
    }


    @GetMapping("/accept/{id}")
    public String acceptForm(@PathVariable Long id) {
        String username = formService.getUsernameForFormRequest(id);
        FormRequest formRequest = FormRequestService.getFormRequestById(id);
        System.out.println(formRequest);
        formService.updateFormRequest(formRequest);
        LocalDate formDate = LocalDate.now();
        formService.grantFormAccess(username, formDate);
        return "redirect:/admin";
    }

    @GetMapping("/get_reviewers/{id}")
    public ModelAndView getReviewers(@PathVariable String id) {
        Form form = formService.getFormById(id);




        System.out.print(graphservic.getCategoryAverages(id));


        ModelAndView modelAndView = new ModelAndView("forms/reviewer");
        modelAndView.addObject("form", form);
        return modelAndView;
    }

    @PostMapping("/submit_reviewers/{id}")
    public ModelAndView submitReviewers(@RequestParam List<String> uniqueEmails, @PathVariable String id) {
        System.out.println("Submitted Emails: " + uniqueEmails);
        formService.addFormReviewers(id, uniqueEmails);
        // TODO: Send email to reviewers   done though slow
        for (String email : uniqueEmails) {
            sendReviewInvitationEmail(email, id);
        }


        System.out.println("reviewrs addd");
        Form form = formService.getFormById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/review/" + id);
        modelAndView.addObject("form", form);
        return modelAndView;
    }

    private void sendReviewInvitationEmail(String to, String formId) {
        Context context = new Context();
        context.setVariable("formId", formId);
        context.setVariable("to", to);
        context.setVariable("url", "http://localhost:8080/review/" + formId);
        String name = formService.getFormById(formId).getUsername();
        context.setVariable("name", name);
        emailService.sendSimpleMessage(to, "Review Form Invitation", "account/fillFormEmail", context);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/review/{formId}")
    public ModelAndView getForm(@PathVariable String formId) {
        Form form = formService.getFormById(formId);
        List<Question> questions = questionServiceInter.getAllQuestions();
        List<Question> textQuestions = questionServiceInter.getTextAreaQuestions();
        ModelAndView modelAndView = new ModelAndView("forms/formImpl");
        modelAndView.addObject("form", form);
        modelAndView.addObject("questions", questions);
        modelAndView.addObject("textQuestions", textQuestions);
        modelAndView.addObject("isOwner", isOwner(form));
        return modelAndView;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/review/{formId}")
    public String submitReview(@PathVariable String formId,
                               @RequestParam("responses") String responses,
                               @RequestParam(value = "email", required = false) String fillerEmail,
                               @RequestParam(value = "Who", required = false) String Relationship,
                               RedirectAttributes redirectAttributes) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Answer> answerList = objectMapper.readValue(responses, new TypeReference<List<Answer>>() {
            });


            List<String> reviewers = formService.getReviewersForAForm(formId);

            if (fillerEmail != null) {


                if (!reviewers.contains(fillerEmail)) {
                    redirectAttributes.addFlashAttribute("flashError", "You are not allowed to fill this form");
                    return "redirect:/review/" + formId;
                }

                if (formService.getIfHasFilledForm(formId, fillerEmail)) {
                    redirectAttributes.addFlashAttribute("flashError", "You have already filled this form");
                    return "redirect:/review/" + formId;
                }
                formService.updateReviewersAfterSubmission(formId, fillerEmail, Relationship);
                answerList.forEach(answer -> answer.setUsername(fillerEmail));

            } else {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String username = authentication.getName();
                if (formService.ifUserHasSelfReviewed(formId, username)) {
                    redirectAttributes.addFlashAttribute("flashError", "You have already filled this form");
                    System.out.println("this code is now working");

                    return "redirect:/review/" + formId;
                }
                answerList.forEach(answer -> answer.setUsername(username));

            }
            answerList.forEach(answer -> answer.setFormId(formId));
            AnswerServiceInter.processAndSaveAnswers(answerList);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flashError", "An error occurred while processing the form");
            return "redirect:/review/" + formId;
        }
        catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flashError", "An error occurred while processing the form");
            return "redirect:/review/" + formId;
        }
        return "redirect:/account";
    }


    Boolean isOwner(Form form) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return form.getUsername().equals(username);
    }


    public Boolean checkCanFillForm(String formId, String email, List<String> reviewers) {

        if (reviewers.contains(email) && formService.getIfHasFilledForm(formId, email)) {
            return true;
        }
        return false;
    }




}
