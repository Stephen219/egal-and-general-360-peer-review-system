package uk.cf.ac.LegalandGeneralTeam11.domain;

import com.structurizr.annotation.UsedByPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@UsedByPerson(name = "admin", description = "manage domains", technology = "http(s)")

@Controller
public class DomainController {
    @Autowired
    private DomainService domainService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/domain")
    public ModelAndView getDomains() {
        ModelAndView modelAndView = new ModelAndView("Domain/domainEditor");
        List<Domain> Domains = domainService.getAllDomains();
        modelAndView.addObject("Domains", Domains);
        return modelAndView;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/domain/add")
    public ModelAndView addDomain() {
        ModelAndView modelAndView = new ModelAndView("Domain/addDomain");
        modelAndView.addObject("domain", new Domain());
        return modelAndView;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addDomain")
    public String addDomain(@ModelAttribute Domain domain) {
        domainService.addDomain(domain);
        return "redirect:/domain";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/domain/edit/{id}")
    public String showEditDomainForm(@PathVariable Long id, Model model) {
        Domain domain = domainService.getDomainById(id);
        model.addAttribute("domain", domain);
        return "Domain/editDomain";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/updateDomain")
    public String updateDomain(@ModelAttribute Domain domain) {
        domainService.updateDomain(domain);
        return "redirect:/domain";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping ("/domain/delete/{id}")
    public String deleteDomain(@PathVariable Long id) {
        domainService.deleteDomain(id);
        return "redirect:/domain";
    }
}

