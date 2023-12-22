package uk.cf.ac.LegalandGeneralTeam11.account;

import com.structurizr.annotation.UsedByPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.cf.ac.LegalandGeneralTeam11.Form.Form;
import uk.cf.ac.LegalandGeneralTeam11.Form.FormServiceImpl;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequest;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestService;
import uk.cf.ac.LegalandGeneralTeam11.Graphs.GraphService;
import uk.cf.ac.LegalandGeneralTeam11.user.User;
import uk.cf.ac.LegalandGeneralTeam11.user.UserService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@UsedByPerson(name = "employees", description = "uses the 360 assessment ", technology = "http(s)")

public class userAccountLinks {
    FormRequestService formRequestService;


    FormServiceImpl formService;

    GraphService graphService;

    UserService userService;

    @Autowired
    public userAccountLinks(FormRequestService formRequestService, FormServiceImpl formservice, GraphService graphService, UserService userService) {
        this.formService = formservice;
        this.formRequestService = formRequestService;
        this.graphService = graphService;
        this.userService = userService;
    }

    /**
     *  This method returns the average score of all forms for each category for a a given user
     *  It is used to populate the graph on the dashboard page
     *   it outputs the data in the endpoint /account/averages/ where it is later fetched by the javascript
     *   idally, this should be done by binding the data to the model and inline javascript acces it but this tampers with the js already written
     * @return
     */

    @GetMapping("/account/averages/")
    public ResponseEntity<List<Map<String, Object>>> getCategoryAverages() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Map<String, Object>> categoryAverages = graphService.getAverageAnswersForUser(username);
        System.out.println(categoryAverages);
        System.out.println(categoryAverages);
        Map<String, Float> averageMap = new HashMap<>();
        categoryAverages.forEach((map) -> {
            averageMap.put((String) map.get("formid"), (Float) map.get("average"));
        });
        System.out.println(averageMap);

        if (categoryAverages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(categoryAverages, HttpStatus.OK);
        }
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





    @GetMapping("/account")
    public ModelAndView getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        String username = currentPrincipalName;
        List<Form> forms = formService.getFormByUser(username);

        String email = userService.getUserByUserName(username).getEmail();
        Long id = userService.getUserByUserName(username).getId();
        List<FormRequest> allFormRequests = formRequestService.getAllByUser(username);

        List<Form> assignedForms = formService.getFormsAssignedToUser(email);

        List<String> adjustedDates = adjustDates(assignedForms);


        List<Map<String, Object>> averages = graphService.getAverageAnswersForUser(username);
        Map<String, Float> averageMap = new HashMap<>();
        averages.forEach((map) -> {
            averageMap.put((String) map.get("formid"), (Float) map.get("average"));
        });

        Map<String, Long> responderCounts = new HashMap<>();
        for (Form form : forms) {
            long responderCount = formService.getTheNumberOfResponsesForAform(form.getId());
            responderCounts.put(form.getId(), responderCount);
        }
        ModelAndView modelAndView = new ModelAndView("account/dashboard");
        modelAndView.addObject("forms", forms);
        modelAndView.addObject("responderCounts", responderCounts);
        modelAndView.addObject("chartData", averageMap);
        modelAndView.addObject("formRequests", allFormRequests);
        modelAndView.addObject("assignedForms", assignedForms);
        modelAndView.addObject("adjustedDates", adjustedDates);
        modelAndView.addObject("username",  userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getUsername());
        return modelAndView;
    }

    @GetMapping("/my_info")
    public ModelAndView getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        String username = currentPrincipalName;
        User user = userService.getUserByUserName(username);
        String email = userService.getUserByUserName(username).getEmail();
        Long id = userService.getUserByUserName(username).getId();
        ModelAndView modelAndView = new ModelAndView("account/user_info");
        modelAndView.addObject("email", email);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

}
