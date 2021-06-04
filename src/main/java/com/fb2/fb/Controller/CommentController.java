package com.fb2.fb.Controller;

import com.fb2.fb.model.Comment;
import com.fb2.fb.model.CommentLike;
import com.fb2.fb.model.Post;
import com.fb2.fb.model.User;
import com.fb2.fb.service.CommentLikeService;
import com.fb2.fb.service.CommentService;
import com.fb2.fb.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class CommentController {

    @Autowired
    private CommentService commentservice;
    @Autowired
    PostService postService;

    @Autowired
    CommentLikeService commentLikeService;

//    @PostMapping("/saveComment")
//    public String saveComment( @ModelAttribute("newComment") Comment comment, HttpServletRequest request) {
//        System.err.println("saving comment  postid = "+ comment.getCommentId() + "comment ="+comment.getComment());
//
//        return "redirect:/facebookHome";
//    }


    @PostMapping("/saveComment/{postId}")
    public String saveComment(@PathVariable(name = "postId") Long postId, @ModelAttribute ("newComment") Comment comment, HttpServletRequest request) {
        System.err.println("saving comment  postid = "+ postId + "comment ="+comment.getComment());
        HttpSession session = request.getSession();
        User userSession = (User) session.getAttribute("user");
        Post post = postService.getPostById(postId);
        commentservice.addComment2(userSession, post, comment.getComment());
        return "redirect:/facebookHome";
    }


    @PostMapping("/saveEditedComment")
    public String saveEditedComment(@ModelAttribute("comment") Comment comment) {
        commentservice.updateComment(comment.getId(),comment.getComment());
        return "redirect:/facebookHome";
    }



    @RequestMapping("/editComment/{postId}/{commentId}")
    public ModelAndView showEditCommentPage(@PathVariable(name = "postId") long postId, @PathVariable(name = "commentId") long commentId,Model model) {
        ModelAndView mav = new ModelAndView("Comment_edit2");
        Post post = postService.getPostById(postId);
        mav.addObject("post",post);
        mav.addObject("commentId",commentId);
        Comment comment = commentservice.get(commentId);
        mav.addObject("comment", comment);
        return mav;
    }






    @RequestMapping("/deleteComment/{commentId}")
    public String deleteComment(@PathVariable(name = "commentId") Long commentId) {
        commentservice.delete(commentId);
        return "redirect:/facebookHome";
    }

    @RequestMapping("/saveCommentLikes/{commentId}")
    public String likeIndex(@PathVariable(name="commentId") Long id, HttpSession session, CommentLike like, Model model) {
        User userObj = (User) session.getAttribute("user");
        if (userObj == null) return "redirect:/login";
        Comment comment = commentservice.getCommentById(id);
        CommentLike commentLike = commentLikeService.getCommentLikeByCommentAndUser(comment, userObj);
        like.setComment(comment);
        like.setUser(userObj);
        if (commentLike == null) {
            commentLikeService.addLike(like);
        } else {
            commentLikeService.deleteLike(like);
        }

        return "redirect:/facebookHome";
    }

}
