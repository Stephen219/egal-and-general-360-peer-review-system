@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String getAddUserForm() {
        // Implement logic to return the template for adding a user
        return "add_user_form";
    }

    @PostMapping("/add")
    public String postAddUserForm(@ModelAttribute User user) {
        // Implement logic to save the user
        userService.addUser(user);
        return "redirect:/";
    }
}
