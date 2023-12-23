package uk.cf.ac.LegalandGeneralTeam11.user;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;
import uk.cf.ac.LegalandGeneralTeam11.emails.EmailService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/admin/add")
    public ModelAndView  getAddUserForm() {

        User user = new User();
        System.out.println(user);
        String baseUrl = getBaseUrl();
        System.out.println(baseUrl);

        ModelAndView n = new ModelAndView("add_user_form");
        n.addObject("user", user);
        return n ;
    }

    @PostMapping("/admin/add")
    public ModelAndView  getUserForm(@Valid User user , BindingResult bindingResult, HttpServletRequest request) {

        ModelAndView m = new ModelAndView("redirect:/admin");
        if (bindingResult.hasErrors()) {
            return new ModelAndView("add_user_form");
        }
        if (userService.checkUserExists(user.getEmail(), user.getUsername())) {
            bindingResult.rejectValue("email", "error.user", "There is already a user registered with the email provided");
            return new ModelAndView("add_user_form");
        }
        userService.save(user);
        String token = userService.generateActivationToken(user, "activation");
        sendTokenEmail(user.getEmail(), token, "Account Activation", "account/activate-email");
        return m;
    }

    private String getBaseUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }

    /**
     * This method is u sed to send an email to the user to activate their account
     * @param to the email address of the user

     */


    private void sendTokenEmail(String to, String token, String subject, String template) {
        User user = userService.getUserByEmail(to);
        String username = user.getUsername();
        Context context = new Context();
        context.setVariable("token", token);
        context.setVariable("to", username);
        context.setVariable("email",user.getEmail());
        context.setVariable("url", getBaseUrl());
        emailService.sendSimpleMessage(to, subject, template, context);
    }


    @GetMapping("/activate/{token}")
    public String activateAccount(Model model, @PathVariable String token)
    {
        try {
            TokenDto activationToken = userService.getActivationToken(token);
            token = activationToken.getToken();
            String email = activationToken.getUserEmail();
            model.addAttribute("token", token);
            model.addAttribute("mail", email);
            if (activationToken != null && userService.checkTokenUsable(token)) {
                return "activateAccount";
            }
        } catch (RuntimeException e) {
            model.addAttribute("activationError", e.getMessage());
            return "activateAccount";
        }
        model.addAttribute("activationError", "Invalid or expired activation token.");
        return "activateAccount";
    }
    /**
     * This method is used to activate the user account
     * @param model the model
     * @param token the token
     * @param password the password
     * @param confirmPassword the confirmPassword
     * @param mail the mail
     * @return String  the view
     */
    @PostMapping("/activate/{token}")
    public String activateAccount(Model model,
                                  @PathVariable String token,
                                  @RequestParam("password") String password,
                                  @RequestParam("confirmPassword") String confirmPassword,
                                  @RequestParam("mail") String mail) {
        TokenDto Token = userService.getActivationToken(token);
        String emailComparison = Token.getUserEmail();
        if (!emailComparison.equals(mail)) {
            model.addAttribute("mail", emailComparison);
            model.addAttribute("activationError", "error with the parameters.");
            return "activateAccount";
        }
        try {
            if (userService.validatePassword(password, confirmPassword)) {
                TokenDto activationToken = userService.getActivationToken(token);
                String email = activationToken.getUserEmail();
                token = activationToken.getToken();
                model.addAttribute("token", token);
                if (activationToken != null && userService.checkTokenUsable(token)) {
                    User user = userService.getUserByEmail(email);
                    user.setPassword(password);
                    userService.encodePassword(user);
                    userService.updateUser(user);
                    userService.setTokenUsed(token);

                    return "redirect:/login";
                }
                System.out.println("Invalid or expired activation token.");
            } else {
                model.addAttribute("activationError", "Passwords do not match");
                return "activateAccount";
            }
        } catch (RuntimeException e) { // specifc exception  i know might be thrown
            model.addAttribute("activationError", e.getMessage());
            return "activateAccount";
        }
        catch (Exception e) { // catch all other exceptions
            model.addAttribute("activationError", "unable to activate account contact admin");
            return "activateAccount";
        }
        model.addAttribute("activationError", "Invalid or expired activation token.");
        return "activateAccount";
    }

    /**
     * get mapping for reset password
     * @return ModelAndView
     */


    @GetMapping("/forgot-password")
    public ModelAndView resetPassword() {
        ModelAndView modelAndView = new ModelAndView("forms/resetPasswordEmailForm");
        return modelAndView;
    }

    /**
     * post mapping for reset password
     * @param email
     * @return ModelAndView
     */

    @PostMapping("/forgot-password")
    public String resetPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes, Model model) {
        if (StringUtils.isBlank(email)) {
            model.addAttribute("error", "Please provide an email address.");
            redirectAttributes.addFlashAttribute("error", "Please provide a valid email address.");
            return "forms/resetPasswordEmailForm";
        }
        try {
            User user = userService.getUserByEmail(email);
            if (user == null) {
                model.addAttribute("error", "No user found with email " + email);
                redirectAttributes.addFlashAttribute("error", "No user found with email " + email);
                return "forms/resetPasswordEmailForm";
            }
            String token = userService.generateActivationToken(user, "reset");
            sendTokenEmail(user.getEmail(), token, "Reset Password", "account/resetPasswordEmail");
            model.addAttribute("message", "An email has been sent to " + email +
                    " with instructions on how to reset your password.");
            redirectAttributes.addFlashAttribute("message", "An email has been sent to " + email +
                    " with instructions on how to reset your password.");
            return "forms/resetPasswordEmailForm";

        }
        catch (RuntimeException e){
            model.addAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "forms/resetPasswordEmailForm";
        }


    }



    @GetMapping("/password/reset-password")
    public String resetPassword(Model model,
                                @RequestParam("token") String token,
                                @RequestParam("email") String email) {
        TokenDto resetToken = userService.getActivationToken(token);
        model.addAttribute("token", resetToken.getToken());
        model.addAttribute("email", resetToken.getUserEmail());

        try {
            if (resetToken != null && userService.checkTokenUsable(token)) {
                return "account/ResetPassword";
            }
        } catch (RuntimeException e) {
            model.addAttribute("resetError", e.getMessage());
            return "account/ResetPassword";
        }

        return "account/ResetPassword";
    }


    @PostMapping("/password/reset-password")
    public String resetPassword(Model model,
                                @RequestParam("token") String token,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("confirmPassword") String confirmPassword) {
        TokenDto resetToken = userService.getActivationToken(token);
        model.addAttribute("token", resetToken.getToken());
        model.addAttribute("email", resetToken.getUserEmail());
        System.out.println("token: " + token);
        System.out.println("email: " + email);
        System.out.println("password: " + password);
        System.out.println("confirmPassword: " + confirmPassword);
        try {
            if (userService.validatePassword(password, confirmPassword)) {
                if (resetToken != null && userService.checkTokenUsable(token)) {
                    User user = userService.getUserByEmail(email);
                    user.setPassword(password);
                    userService.encodePassword(user);
                    userService.updateUser(user);
                    userService.setTokenUsed(token);
                    return "redirect:/login";
                }
            } else {
                model.addAttribute("resetError", "Passwords do not match");
                return "account/ResetPassword";
            }
        } catch (RuntimeException e) {
            model.addAttribute("resetError", e.getMessage());
            return "account/ResetPassword";
        }
        model.addAttribute("resetError", "Invalid or expired reset token.");
        return "account/ResetPassword";
    }

    @GetMapping("my_info/update-password")
    public String showUpdatePasswordForm(Model model, Authentication authentication) {
        String userEmail = authentication.getName();
        model.addAttribute("userEmail", userEmail);
        return "account/update_password";
    }


    @PostMapping("/my_info/update-password")
    public String updatePassword(Model model,
                                 @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Authentication authentication, RedirectAttributes redirectAttributes) {
        String userEmail = authentication.getName();
        model.addAttribute("userEmail", userEmail);
        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword) || StringUtils.isBlank(confirmPassword)) {
            model.addAttribute("updateError", "Please provide all the required fields.");
            return "account/update_password";
        }
        try {
            if (userService.validateChangePassword(oldPassword, userEmail, newPassword) && userService.validatePassword(newPassword, confirmPassword)) {
                User user = userService.getUserByEmail(userEmail);
                user.setPassword(newPassword);
                userService.encodePassword(user);
                userService.updateUser(user);
                redirectAttributes.addFlashAttribute("message", "Password updated successfully.");
                return "redirect:/login";
            }
                return "account/update_password";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("updateError", e.getMessage());
            return "redirect:/my_info/update-password";
        }
    }











    @GetMapping("admin/manage_employees")
    public ModelAndView manageEmployees() {

        List<User> users = userService.getUsers();

        System.out.println(users);

        ModelAndView modelAndView = new ModelAndView("account/manage_employees");
        modelAndView.addObject("users", users);
        return modelAndView;
    }




    @GetMapping("/admin/manage_employees/edit/{id}")
    public String showusereditForm(@PathVariable Long id, Model model) {
        User user = userService.getUser(id);
        System.out.println(user);

        model.addAttribute("user", user);
        model.addAttribute("roleId", user.getRoleId());
        return "account/edit_user";
    }
    @PostMapping("/admin/manage_employees/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        userService.updateUser(user);
        System.out.println(user);
        return "redirect:/admin/manage_employees";
    }
}
