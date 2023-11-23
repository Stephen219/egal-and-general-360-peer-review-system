package uk.cf.ac.LegalandGeneralTeam11.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class accountController {
    @GetMapping("/account")
    public String getAccount() {
        return "account/dashboard";
    }
}
