package com.fb2.fb.Controller;


import com.fb2.fb.model.*;
import com.fb2.fb.service.CommentLikeService;
import com.fb2.fb.service.CommentService;
import com.fb2.fb.service.PostLikeService;
import com.fb2.fb.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;



@Controller
public class PostLikeController {

    @Autowired
    private PostLikeService postLikeService;
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;
    @Autowired
    CommentLikeService commentLikeService;

    @PostMapping("/savePostLikes/{postId}")
    public String likeIndex(@PathVariable("postId") Long postId, HttpSession session, PostLike like, Model model) {
        System.err.println(postId);
        User userObj = (User) session.getAttribute("user");
        if (userObj == null) return "redirect:/login";
        Post post = postService.getPostById(postId);
        PostLike postLike = postLikeService.getPostLikeByPostAndUser(post, userObj);

        like.setPost(post);
        like.setUser(userObj);
        if (postLike == null) {
            postLikeService.addLike(like);
        } else {
            postLikeService.deleteLike(postLike);
        }
        return "redirect:/facebookHome";
    }

    @PostMapping("/saveCommentLikes/{commentId}")
    public String likeIndex(@PathVariable("commentId") Long commentId, HttpSession session, CommentLike like, Model model) {
        User userObj = (User) session.getAttribute("user");
        if (userObj == null) return "redirect:/login";
        Comment comment = commentService.getCommentById(commentId);
        CommentLike commentLike = commentLikeService.getCommentLikeByPostAndUser(comment, userObj);
        like.setComment(comment);
        like.setUser(userObj);
        if (commentLike == null) {
            commentLikeService.addLike(like);
        } else {
            commentLikeService.deleteLike(like);
        }
        return "redirect:/facebookHome";
    }




//    @PostMapping("/savePostLike/{postId}")
//    public String savePostLike(@PathVariable(name = "postId") long postId,HttpServletRequest request) {
//        System.out.println("i got here");
//
//        HttpSession session = request.getSession();
//        User userSession = (User) session.getAttribute("user");
//        Post post = postService.getPostById(postId);
//        int likes = 1;
//        System.err.println(userSession.getUserid() + " "+ post.getPostId() + " "+ likes);
//        postLikeService.addLikes(likes,post ,userSession );
//
//        return "redirect:/facebookHome";
//    }


}