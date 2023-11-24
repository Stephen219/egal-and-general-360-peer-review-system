package uk.cf.ac.LegalandGeneralTeam11.Login;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginForm", new ModelAndView());
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model) {
        boolean isAuthenticated = userService.authenticate(username, password);

        if (isAuthenticated) {
            // Successful login
            return "redirect:/hello"; // Redirect to home page
        } else {
            // Failed login, display error message
            model.addAttribute("error", "Invalid credentials. Login failed.");
            return "login"; // Redirect back to the login page with an error message
        }
    }
}