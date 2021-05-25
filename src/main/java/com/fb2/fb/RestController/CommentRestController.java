package com.fb2.fb.RestController;


import com.fb2.fb.Exception.ResourceNotFoundException;
import com.fb2.fb.model.Comment;
import com.fb2.fb.model.Post;
import com.fb2.fb.model.User;
import com.fb2.fb.service.CommentService;
import com.fb2.fb.service.PostService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/comment")
@Api(value="Comment Resource Endpoint")
public class CommentRestController {

    @Autowired
    CommentService commentService;
    @Autowired
    PostService postService;

    @Autowired
    public CommentRestController(CommentService commentService){
        this.commentService = commentService;
    }

    //working
    @PostMapping(path = "/{postId}/{commentbody}")
    public ResponseEntity<?> createComment(@PathVariable("postId") Long postId,@PathVariable("commentbody") String comment, HttpSession session) {
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null) {
            throw new ResourceNotFoundException("No user found in the session please make sure you are logged in" );
        }
        Post post =  postService.getPostById(postId);
//        Post post = postService.getPostById(postId);
        System.err.println("i got here");
        Comment comment1 = new Comment();
        comment1.setComment(comment);
        comment1.setPost(post);
//        comment1.setUser(authenticatedUser);
        commentService.addComment(comment1);
        return ResponseEntity.ok().build();
    }



    //working
    @PutMapping("/{commentId}/{commentbody}")
    public ResponseEntity<?> updateComment(@PathVariable("commentId")long commentId,@PathVariable("commentbody") String comment,  HttpSession session) {
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null) {
            throw new ResourceNotFoundException("No user found in the session please make sure you are logged in" );}
        if(!commentService.checkExistence(commentId)){
            throw new ResourceNotFoundException("Post not found with id " + commentId);
        }
        Comment comment1 = commentService.getCommentById(commentId);
        commentService.editComment(comment1,comment);
        return ResponseEntity.ok().build();
    }

    // working
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable (name = "commentId") Long commentId) {
        if(!commentService.checkExistence(commentId)){
            throw new ResourceNotFoundException("Post not found with id " + commentId);
        }
        commentService.delete(commentId);
        return ResponseEntity.ok().build();
    }


}

