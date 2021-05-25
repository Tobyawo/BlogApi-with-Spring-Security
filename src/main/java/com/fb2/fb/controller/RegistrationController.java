package com.fb2.fb.controller;

import com.fb2.fb.model.User;
import com.fb2.fb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String getRegistrationPage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String signUp(User user, Model model) {
        User userRegistered = userService.getUserByEmail(user.getEmail());
        if (userRegistered != null) {
            model.addAttribute("failed", "User with Email Already exist");
            return "register";
        }
        userService.addUser(user);
        return "redirect:/login";
    }
}

