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
import org.thymeleaf.context.Context;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequest;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestService;
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
        ModelAndView modelAndView = new ModelAndView("forms/reviewer");
        modelAndView.addObject("form", form);
        return modelAndView;
    }

    @PostMapping("/submit_reviewers/{id}")
    public ModelAndView submitReviewers(@RequestParam List<String> uniqueEmails, @PathVariable String id) {
        System.out.println("Submitted Emails: " + uniqueEmails);
        formService.addFormReviewers(id, uniqueEmails);
        // TODO: Send email to reviewers
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

    public String submitReview(@PathVariable String formId,@RequestParam("responses") String responses, @RequestParam(value = "email", required = false) String fillerEmail, @RequestParam(value = "Who", required = false) String Relationship) {
        System.out.println(responses);
        System.out.println(fillerEmail);
        System.out.println(Relationship);






        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Answer> answerList = objectMapper.readValue(responses, new TypeReference<List<Answer>>() {});

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            if (fillerEmail != null) {
                answerList.forEach(answer -> answer.setUsername(fillerEmail));
            } else {
                answerList.forEach(answer -> answer.setUsername(username));
            }






            //75answerList.forEach(answer -> answer.setUsername(username));

            // Set the formId for each answer
            answerList.forEach(answer -> answer.setFormId(formId));

//            answerList.forEach(answer -> answer.setFormId(Long.parseLong(formId)));

            // Save the answers to the database
            System.out.println(answerList);
            AnswerServiceInter.processAndSaveAnswers(answerList);
            System.out.println("huuuuuuuuuuuufieggfbvhjvbvbvhbhbbvhjbvhjbf,bfd");

        } catch (JsonProcessingException e) {
            e.printStackTrace();

        }












        Form form = formService.getFormById(formId);

        ModelAndView modelAndView = new ModelAndView("redirect:/account");
        modelAndView.addObject("form", form);
//        System.out.println(answers);





        // Redirect to a success page or another appropriate view
        return "redirect:/account";



    }


    Boolean isOwner(Form form) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return form.getUsername().equals(username);
    }


}
