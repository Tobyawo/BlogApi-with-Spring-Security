package com.fb2.fb.controller;

import com.fb2.fb.model.*;
import com.fb2.fb.service.CommentLikeService;
import com.fb2.fb.service.CommentService;
import com.fb2.fb.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    CommentLikeService commentLikeService;

    @GetMapping("/comment/{postId}")
    public String getCommentPage(@PathVariable("postId") Long postId, Model model, HttpSession httpSession) {
        Post post = postService.getPostById(postId);
        User user = (User)httpSession.getAttribute("user");
        model.addAttribute("post", post);
        model.addAttribute("user", user);
        model.addAttribute("newComment", new Comment());
        model.addAttribute("newCommentlike", new CommentLike());
        model.addAttribute("deleteComment", new Comment());
        model.addAttribute("allComment", commentService.findAll(postId));
        return "comment";
    }


    @PostMapping("/comment/{id}")
    public String newComment(@PathVariable("id") Long id, HttpSession session,  String comment) {
        Comment comment1 = new Comment();

        Object userObj = session.getAttribute("user");
        if (userObj == null) return "redirect:/login";

        comment1.setComment(comment);
        comment1.setPost(postService.getPostById(id));
        comment1.setUser((User) userObj);
        commentService.addComment(comment1);
        return "redirect:/home";
    }

    @GetMapping("/comment/edit/{commentId}")
    public String getEditCommentPage(@PathVariable("commentId") Long commentId, Model model, HttpSession httpSession) {
        Comment comment = commentService.getCommentById(commentId);
        User user = (User)httpSession.getAttribute("user");
        model.addAttribute("comm", comment);
        model.addAttribute("user", user);
        model.addAttribute("newComment", new Comment());
        return "editComment";
    }

    @PostMapping("/comment/edit/{commentId}")
    public String editComment(@PathVariable("commentId") long id, HttpSession session, @ModelAttribute("comm") Comment commentUpdate) {
        Object userObj = session.getAttribute("user");
        if (userObj == null) return "redirect:/login";

        Comment comment = commentService.getCommentById(id);
        commentService.editComment(comment, commentUpdate.getComment());
        return "redirect:/home";
    }


    @PostMapping("/comment/delete/{id}")
    public String deletePost(HttpSession httpSession, @PathVariable("id") Long id){
        User user = (User) httpSession.getAttribute("user");
        if(user == null)
            return "home";
        Comment comment = commentService.getCommentById(id);
        commentService.deleteComment(comment);
        return "redirect:/home";
    }


    @PostMapping("/comment/like/{id}")
    public String likeIndex(@PathVariable("id") Long id, HttpSession session, CommentLike like, Model model) {
        User userObj = (User) session.getAttribute("user");
        if (userObj == null) return "redirect:/login";
        Comment comment = commentService.getCommentById(id);
        CommentLike commentLike = commentLikeService.getCommentLikeByCommentAndUser(comment, userObj);
        like.setComment(comment);
        like.setUser(userObj);
        if (commentLike == null) {
            commentLikeService.addLike(like);
        } else {
            commentLikeService.deleteLike(like);
        }

        return "redirect:/home";
    }

}

