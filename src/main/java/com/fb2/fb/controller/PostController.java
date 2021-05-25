package com.fb2.fb.controller;

import com.fb2.fb.model.*;
import com.fb2.fb.service.PostLikeService;
import com.fb2.fb.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    PostLikeService likeService;

    @PostMapping("/post")
    public String makePost(HttpSession httpSession, Model model, Post post) {
        User user = (User)httpSession.getAttribute("user");

        if (post.getMessage().length() == 0) {
            model.addAttribute("emptyPost", "Post can not be empty");
        }
        postService.addPost(post, user);
        return "redirect:/home";
    }

    @PostMapping("/delete/{id}")
    public String deletePost(HttpSession httpSession, @PathVariable("id") Long id){
        User user = (User) httpSession.getAttribute("user");
        if(user == null)
            return "home";
        Post post = postService.getPostById(id);
        postService.deletePost(post);
        return "redirect:/home";
    }

    @PostMapping("/like/{id}")
    public String likeIndex(@PathVariable("id") Long id, HttpSession session, PostLike like, Model model) {
        User userObj = (User) session.getAttribute("user");
        if (userObj == null) return "redirect:/login";
        Post post = postService.getPostById(id);
        PostLike postLike = likeService.getPostLikeByPostAndUser(post, userObj);
        like.setPost(post);
        like.setUser(userObj);
        if (postLike == null) {
            likeService.addLike(like);
        } else {
            likeService.deleteLike(postLike);
        }

        return "redirect:/home";
    }

    @GetMapping("/edit/{postId}")
    public String getEditPostPage(@PathVariable("postId") Long postId, Model model, HttpSession httpSession) {
        Post post = postService.getPostById(postId);
        User user = (User)httpSession.getAttribute("user");
        model.addAttribute("post", post);
        model.addAttribute("user", user);
        model.addAttribute("newPost", new Post());
        return "editPost";
    }

    @PostMapping("/edit/{postId}")
    public String editPost(@PathVariable("postId") Long id, HttpSession session, Post postUpdate) {
        System.out.println(id);
        System.out.println(postUpdate.getMessage());
        System.out.println(postUpdate.toString());
        Object userObj = session.getAttribute("user");
        if (userObj == null) return "redirect:/login";

        Post post = postService.getPostById(id);
        postService.editPost(post, postUpdate.getMessage());
        return "redirect:/home";
    }
}
