package uk.cf.ac.LegalandGeneralTeam11.Graphs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import uk.cf.ac.LegalandGeneralTeam11.Form.Form;
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
    public ResponseEntity<List<Map<String, Object>>> getCategoryAverages(@PathVariable String id) {
        //returns a list of category averages
        List<Map<String, Object>> categoryAverages =graphservice.getCategoryAverages(id);

        if (categoryAverages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(categoryAverages, HttpStatus.OK);
        }
    }
//        ModelAndView modelAndView = new ModelAndView("account/graphs", "graphs", graphservice.getCategoryAverages(formid));
//        modelAndView.addObject("form", form);
//        return modelAndView;
     }

