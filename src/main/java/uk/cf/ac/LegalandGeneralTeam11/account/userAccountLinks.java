package uk.cf.ac.LegalandGeneralTeam11.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.cf.ac.LegalandGeneralTeam11.Form.Form;
import uk.cf.ac.LegalandGeneralTeam11.Form.FormServiceImpl;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestService;
import uk.cf.ac.LegalandGeneralTeam11.Graphs.GraphService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller

public class userAccountLinks {
    FormRequestService formRequestService;


    FormServiceImpl formService;

    GraphService graphService;

    @Autowired
    public userAccountLinks(FormRequestService formRequestService, FormServiceImpl formservice, GraphService graphService) {
        this.formService = formservice;
        this.formRequestService = formRequestService;
        this.graphService = graphService;
    }

    @GetMapping("/account/my_forms")
    public ModelAndView getAllUserForms( @RequestParam(name = "sortBy", required = false) String sortBy) {
        ModelAndView modelAndView = new ModelAndView("account/allUserForms");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        List<String> adjustedDates = adjustDates(formService.getFormByUser(currentPrincipalName));
        Map<String, Long> responderCounts = countResponders(formService.getFormByUser(currentPrincipalName));
        modelAndView.addObject("forms", formService.getFormByUser(currentPrincipalName));
        modelAndView.addObject("adjustedDates", adjustedDates);
        modelAndView.addObject("responderCounts", responderCounts);
        return modelAndView;
    }


    /**
     * this is the method that gets the thymeleaf deadline for each form.
     * ideally, it would be done in the db by introducing a new column of deadline or using thymeleaf dates
     * but for now, this is the solution
     *
     * @param forms the list of forms
     * @return
     */
    public  List<String> adjustDates(List<Form> forms) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return forms.stream()
                .map(form -> {
                    LocalDateTime localDateTime = form.getFormDate().atStartOfDay();
                    Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
                    LocalDateTime adjustedDateTime = localDateTime.plusWeeks(2);
                    return adjustedDateTime.format(formatter);
                })
                .collect(Collectors.toList());
    }

    /**
     * this is the method that counts the `number of responders for a form
     * @param forms
     * @return
     */

    public Map<String, Long> countResponders(List<Form> forms) {
        Map<String, Long> responderCounts = new HashMap<>();
        for (Form form : forms) {
            long responderCount = formService.getTheNumberOfResponsesForAform(form.getId());
            responderCounts.put(form.getId(), responderCount);
        }
        return responderCounts;
    }

}
