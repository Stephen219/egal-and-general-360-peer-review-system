package uk.cf.ac.LegalandGeneralTeam11.Graphs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uk.cf.ac.LegalandGeneralTeam11.Form.FormService;

import java.util.List;
import java.util.Map;

@Controller
public class GraphController{

    @Autowired
    GraphService graphservice;
    @Autowired
    FormService formservice;


    @GetMapping("/account/averages/{id}")
    public String getCategoryAverages(@PathVariable String id, Model model) {
        List<Map<String, Object>> categoryAverages = graphservice.getCategoryAverages(id);
        Map<String, List<String>> formTextAnswer = graphservice.getFormTextAnswer(id);
        System.out.println(formTextAnswer);
        model.addAttribute("formTextAnswer", formTextAnswer);
        model.addAttribute("categoryAverages", categoryAverages);
        return "userGraphs";
    }

}

