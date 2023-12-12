package uk.cf.ac.LegalandGeneralTeam11.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/add")
    public ModelAndView  getAddUserForm() {

        //User user = new User(0L, "HJJSJNBDJCDC ", " CJDBCHJDHBCJDC", " 78378272778",2L);
        User user = new User();
        System.out.println(user);
        System.out.println("inrfnjnf get maopjedjed");

        ModelAndView n = new ModelAndView("add_user_form");
        n.addObject("user", user);
        return n ;
    }

    @PostMapping("/admin/add")
    public ModelAndView  getUserForm(User user) {

        userService.save(user);

        System.out.print(user);
        System.out.print("we are arfbfmnbvhjfdvbfmnvjdfhjbfrew");
        ModelAndView m = new ModelAndView("redirect:/");

        return m;
    }
}
