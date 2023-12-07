package uk.cf.ac.LegalandGeneralTeam11.Graphs;

import org.springframework.web.bind.annotation.GetMapping;

public class GraphController {

    @GetMapping("/account/{formid}/graphs")
    public String getGraphs() {

        return "account/graphs";
    }
}
