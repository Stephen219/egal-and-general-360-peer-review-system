package uk.cf.ac.LegalandGeneralTeam11.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.cf.ac.LegalandGeneralTeam11.Form.Form;
import uk.cf.ac.LegalandGeneralTeam11.Form.FormServiceImpl;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller


public class adminLinks {
    FormRequestService formRequestService;


    FormServiceImpl formService;

    @Autowired
    public adminLinks(FormRequestService formRequestService, FormServiceImpl formservice) {
        this.formService = formservice;
        this.formRequestService = formRequestService;
    }

    @GetMapping("admin/all_forms")
    public ModelAndView getAllForms(@RequestParam(name = "sortBy", required = false) String sortBy) {
        System.out.println(sortBy);
        System.out.println("hello we are here gettinjf the " + sortBy + " forms");
        ModelAndView modelAndView = new ModelAndView("account/allForms");
        List<Form> forms = getSortedForms(sortBy, formService.getAllForms());
        Map<String, Long> responderCounts = new HashMap<>();
        for (Form form : forms) {
            long responderCount = formService.getTheNumberOfResponsesForAform(form.getId());
            responderCounts.put(form.getId(), responderCount);
        }
        //TODO: refactor this to a method it is all in the user account links
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        List<String> adjustedDates = forms.stream()
                .map(form -> {
                    LocalDateTime localDateTime = form.getFormDate().atStartOfDay();
                    Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
                    LocalDateTime adjustedDateTime = localDateTime.plusWeeks(2);
                    return adjustedDateTime.format(formatter);
                })
                .collect(Collectors.toList());

        modelAndView.addObject("forms", forms);
        modelAndView.addObject("adjustedDates", adjustedDates);
        modelAndView.addObject("responderCounts", responderCounts);
        return modelAndView;
    }

    /**
     * This method sorts the forms based on the sortBy parameter passed in the url query but if the sortBy parameter is null, it sorts the forms by the date
     * can be moved to a service class
     * @param sortBy the parameter to sort the forms by
     * @return a list of sorted forms
     */
     List<Form> getSortedForms(String sortBy, List<Form> forms) {

        if (sortBy == null) {
            sortBy = "atoz";
        }
        switch (sortBy) {
            case "atoz":
                Collections.sort(forms, Comparator.comparing(Form::getUsername));
                break;
            case "ztoa":
                Collections.sort(forms, Comparator.comparing(Form::getUsername).reversed());
                break;
            case "statuss":
                Collections.sort(forms, Comparator.comparing(Form::getProgressStatus));
                break;
            case "earliest":
                Collections.sort(forms, Comparator.comparing(Form::getFormDate));
                break;
            default:
                Collections.sort(forms, Comparator.comparing(Form::getFormDate).reversed());
                break;
        }
        return forms;
    }
}

