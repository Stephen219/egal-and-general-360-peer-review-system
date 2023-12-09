package uk.cf.ac.LegalandGeneralTeam11.Graphs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import uk.cf.ac.LegalandGeneralTeam11.Form.Form;
import uk.cf.ac.LegalandGeneralTeam11.Form.FormService;

import java.util.ArrayList;
import java.util.HashMap;
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

        List<Map<String, Object>> chartData = graphservice.getChartData(id);
        model.addAttribute("chartData", chartData);
        System.out.println("we are here geting the chart dsta");
        System.out.println(chartData);
        chartData.forEach((map) -> {
            System.out.println(map.get("category"));
            System.out.println(map.get("average"));
        });
        model.addAttribute("formTextAnswer", formTextAnswer);
        model.addAttribute("categoryAverages", categoryAverages);
        return "account/graphs";
    }


}

