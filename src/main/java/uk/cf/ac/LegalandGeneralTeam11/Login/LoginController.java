package uk.cf.ac.LegalandGeneralTeam11.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login/login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("loginForm") @RequestParam String username, @RequestParam String password, Model model) {
        boolean isAuthenticated = userService.authenticate(username, password);

        if (isAuthenticated) {
            // Successful login
            return "redirect:/home"; // Redirect to home page
        } else {
            // Failed login, display error message
            model.addAttribute("loginForm", new LoginForm()); // Add loginForm back to the model
            model.addAttribute("error", "Invalid credentials. Login failed.");
            return "login/login"; // Redirect back to the login page with an error message
        }
    }
}