//package uk.cf.ac.LegalandGeneralTeam11.Graphs;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import uk.cf.ac.LegalandGeneralTeam11.Form.FormService;
//
//import java.util.List;
//import java.util.Map;
//
//@Controller
//public class GraphController
//
//    @Autowired
//    GraphService graphservic;
//@Autowired
//FormService formservice;
//
//    @GetMapping("/account/{formid}/graphs")
//    public List<Map<String, Object>> getCategoryAverages(@PathVariable String formid) {
//        formservice.getFormById(formid);
//        System.out.print(graphservic.getCategoryAverages(formid));
//        graphservic.getCategoryAverages(formid);
//        return graphservic.getCategoryAverages(formid);
//     }
//}
