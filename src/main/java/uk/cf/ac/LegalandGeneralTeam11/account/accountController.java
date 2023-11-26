package uk.cf.ac.LegalandGeneralTeam11.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller

public class accountController {
    @GetMapping("/account")
    public String getAccount() {
        return "account/dashboard";
    }

    @GetMapping("/admin")
    public String home() {

        return "testfile";
    }
}
