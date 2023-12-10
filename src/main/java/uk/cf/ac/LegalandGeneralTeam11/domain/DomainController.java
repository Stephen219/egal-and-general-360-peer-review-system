package uk.cf.ac.LegalandGeneralTeam11.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DomainController {
    @Autowired
    private DomainService domainService;


    @GetMapping("/domain")
    public ModelAndView getMenu() {
        ModelAndView modelAndView = new ModelAndView("account/domainEditor");
        List<Domain> Domains = domainService.getAllDomains();
        modelAndView.addObject("Domains", Domains);
        System.out.println(Domains);
        return modelAndView;
    }
}

