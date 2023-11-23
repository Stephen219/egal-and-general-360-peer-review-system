package uk.cf.ac.LegalandGeneralTeam11;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    // Create a user object with a username and password
    private User user = new User("user", "password");

    @GetMapping("/login")
    public String login() {
        return "login"; // This will return the login.html template
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, @RequestParam String password, Model model) {
        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
            model.addAttribute("username", username); // Add the username to the model
            return "/home"; // Redirect to the home page upon successful login
        } else {
            model.addAttribute("error", "Incorrect details"); // Add an error attribute to the model
            return "/login"; // Return the login page with an error parameter
        }
    }

    @GetMapping("/home")
    public String home(Model model) {
        // Retrieve the username from the model and pass it to the home page
        if (model.containsAttribute("username")) {
            String username = (String) model.getAttribute("username");
            model.addAttribute("username", username);
            return "home"; // This will return the home.html Thymeleaf template
        } else {
            // Handle the case where username attribute is not present
            return "redirect:/login"; // Redirect to the login page
        }
    }
}