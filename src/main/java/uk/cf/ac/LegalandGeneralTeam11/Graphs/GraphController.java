package uk.cf.ac.LegalandGeneralTeam11.Graphs;

import com.structurizr.annotation.UsedByPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.cf.ac.LegalandGeneralTeam11.Form.FormService;
import uk.cf.ac.LegalandGeneralTeam11.answers.AnswerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@UsedByPerson(name = "admin", description = "view the results", technology = "http(s)")
@UsedByPerson(name = "admin", description = "view the results", technology = "http(s)")

@Controller
public class GraphController{

    @Autowired
    GraphService graphservice;
    @Autowired
    FormService formservice;

    @Autowired
    AnswerService answerService;


    @GetMapping("/account/averages/{id}")
    public String getCategoryAverages(@PathVariable String id, Model model) {
        List<Map<String, Object>> categoryAverages = graphservice.getCategoryAverages(id);
        Map<String, List<String>> formTextAnswer = graphservice.getFormTextAnswer(id);

        List<Map<String, Object>> chartData = graphservice.getChartData(id);
        model.addAttribute("chartData", chartData);
        System.out.println("we are here getting the chart data");
        System.out.println(chartData);
        chartData.forEach((map) -> {
            System.out.println(map.get("category"));
            System.out.println(map.get("average"));
        });

        List<Map<String, Object>> relationshipCounts = graphservice.getRelationshipCounts();
        model.addAttribute("relationshipCounts", relationshipCounts);
        System.out.println(relationshipCounts);
        relationshipCounts.forEach((map) -> {
            System.out.println(map.get("relationship"));
            System.out.println(map.get("count"));
        });


        model.addAttribute("formTextAnswer", formTextAnswer);
        model.addAttribute("categoryAverages", categoryAverages);
        return "account/userGraphs";


    }
    @GetMapping("admin/results/{id}")
    public String getResults(@PathVariable String id, Model model) {
        List<Map<String, Object>> graphInt = graphservice.getCategoryAverages(id);
        Map<String, List<String>> graphString = graphservice.getFormTextAnswer(id);
        List<String> reviewers = answerService.GetAllReviewers(id);
        System.out.println(reviewers);
        System.out.println(graphInt);
        System.out.println(graphString);
        model.addAttribute("Reviewers", reviewers);
        model.addAttribute("NumberAnswer", graphInt);
        model.addAttribute("TextAnswer", graphString);
        return "account/seeAllResults";
    }

}

