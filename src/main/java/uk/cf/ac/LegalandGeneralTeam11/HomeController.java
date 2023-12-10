package uk.cf.ac.LegalandGeneralTeam11;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String showHomePage() {

        return "fragments/testfile";
    }
}


