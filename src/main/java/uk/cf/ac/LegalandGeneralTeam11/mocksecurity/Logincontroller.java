package uk.cf.ac.LegalandGeneralTeam11.mocksecurity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller

public class Logincontroller {
    @GetMapping("/login")
    public String login() {
        return "login";
    }


}
