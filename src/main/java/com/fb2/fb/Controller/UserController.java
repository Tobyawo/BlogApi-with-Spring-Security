package com.fb2.fb.Controller;


import java.util.Arrays;
import java.util.List;


import com.fb2.fb.model.User;
import com.fb2.fb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
//@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService service;

//    1
    @GetMapping("/")  // when a request link to loginup page is clicked on. it come here
    public String showloginForm(Model model) {
        User user = new User(); // A User object to represent the information in the form.
        model.addAttribute("user", user);
        model.addAttribute("invalid", null);
        return "loginPage";
    }


    //2
    @RequestMapping(value = "/loginPost", method= RequestMethod.POST)
    public String checkUserlogin(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
        System.err.println("user that login = "+user.getEmail()+user.getPassword());
        User user1 = service.getUserByEmail(user.getEmail());
        System.out.println("user1 email = " + user1.getEmail());
        if (user1 == null) {
            model.addAttribute("invalid", "User does not exist. check login details or register.");
            return "loginPage";
        }

        user1 = service.getUserByEmailAndPassword(user.getEmail(), user.getPassword());
        System.err.println("email = "+ user.getEmail() + "password = " +  user.getPassword());
        if (user1 == null) {
            System.err.println("user found not" );
            model.addAttribute("invalid", "Incorrect password");
            return "loginPage";
        }
        httpSession.setAttribute("user", user1);
        return "redirect:/facebookHome";
    }

    @GetMapping("/signUp")
    public String showSignUpForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "SignUpPage";
    }

    @PostMapping("/handleSignUp")
    public String submitSignUpForm(@ModelAttribute("user") User user) {
        service.save(user);

        ModelAndView modelAndView = new ModelAndView("loginPage");
        modelAndView.addObject("user",user);
        return "redirect:/";
    }


}
