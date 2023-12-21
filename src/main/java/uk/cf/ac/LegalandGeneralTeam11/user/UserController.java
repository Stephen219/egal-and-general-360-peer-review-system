package uk.cf.ac.LegalandGeneralTeam11.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/add")
    public ModelAndView  getAddUserForm() {

        User user = new User();
        System.out.println(user);
        System.out.println("inrfnjnf get maopjedjed");

        ModelAndView n = new ModelAndView("add_user_form");
        n.addObject("user", user);
        return n ;
    }

    @PostMapping("/admin/add")
    public ModelAndView  getUserForm(User user) {

        System.out.println(user);
        System.out.println(user);

        userService.save(user);

        ModelAndView m = new ModelAndView("redirect:/");

        return m;
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
