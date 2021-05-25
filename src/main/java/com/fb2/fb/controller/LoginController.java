package com.fb2.fb.controller;

import com.fb2.fb.model.*;
import com.fb2.fb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    PostLikeService postLikeService;

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("invalid", null);
        model.addAttribute("newRegistration", null);
        return "login";
    }


    @PostMapping("/login")
    public String login(HttpSession httpSession, User user, Model model) {
        User user1 = userService.getUserByEmail(user.getEmail());
        if (user1 == null) {
            model.addAttribute("invalid", "User does not exist. Check login details or register.");
            return "login";
        }
        user1 = userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if (user1 == null) {
            model.addAttribute("invalid", "Incorrect password");
            return "redirect:/login";
        }
        httpSession.setAttribute("user", user1);
        return "redirect:/home";
    }


    @GetMapping("/home")
    public  String getHomePage(Model model, HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        if(user == null) return "redirect:/login";
        System.err.println(user);
        Iterable<Post> posts = postService.getAllPost();
        model.addAttribute("newPost", new Post());
        model.addAttribute("postdelete", new Post());
        model.addAttribute("newPostlike", new PostLike());
        model.addAttribute("postUpdate", new Post());
        model.addAttribute("thisUser", user);
        model.addAttribute("allPosts", posts);
        model.addAttribute("newComment", new Comment());

        return "home";
    }


    @GetMapping("/logout")
    public String loggingOut(Model model, HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        model.addAttribute("user", new User());
        model.addAttribute("invalid", null);
        return "redirect:/login";
    }
}

