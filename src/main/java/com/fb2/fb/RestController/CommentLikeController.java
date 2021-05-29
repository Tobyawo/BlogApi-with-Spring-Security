package com.fb2.fb.RestController;

import com.fb2.fb.Exception.ResourceNotFoundException;
import com.fb2.fb.model.*;
import com.fb2.fb.service.CommentLikeService;
import com.fb2.fb.service.CommentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "/api/v1/commentLike")
@Api(value="Like Resource Rest Endpoint")
public class CommentLikeController {


    @Autowired
    CommentService commentService;

    @Autowired
    CommentLikeService commentLikeService;


    @PostMapping("/{commentId}")
    public ResponseEntity<?> likeComment(@PathVariable("commentId") Long commentId, HttpSession session, CommentLike like) {
        User userObj = (User) session.getAttribute("user");
        if (userObj == null) {
            throw new ResourceNotFoundException("No User found with in the session");}

        if(!commentService.checkExistence(commentId)){
            throw new ResourceNotFoundException("Comment not found with id " + commentId);
        }

        Comment comment = commentService.getCommentById(commentId);
        CommentLike commentLike = commentLikeService.getCommentLikeByCommentAndUser(comment, userObj);
        like.setComment(comment);
        like.setUser(userObj);
        if (commentLike == null) {
            commentLikeService.addLike(like);
            System.err.println("successfully like a comment");
        }
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> unlikeComment(@PathVariable("commentId") Long commentId, HttpSession session, CommentLike commentlike) {
        User userObj = (User) session.getAttribute("user");
        if (userObj == null) {
            throw new ResourceNotFoundException("No User found with in the session");
        }
        if(!commentService.checkExistence(commentId)){
            throw new ResourceNotFoundException("Comment not found with id " + commentId);
        }
        Comment comment = commentService.getCommentById(commentId);
        CommentLike commentLike = commentLikeService.getCommentLikeByCommentAndUser(comment, userObj);

        if(commentLike != null ){
            commentLikeService.deleteLike(commentLike);
            System.err.println("successfully Unlike a comment");
        }return ResponseEntity.ok().build();
    }


}

