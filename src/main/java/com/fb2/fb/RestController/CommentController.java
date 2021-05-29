package com.fb2.fb.RestController;


import com.fb2.fb.Exception.ResourceNotFoundException;
import com.fb2.fb.model.Comment;
import com.fb2.fb.model.Post;
import com.fb2.fb.model.User;
import com.fb2.fb.service.CommentService;
import com.fb2.fb.service.PostService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "/api/v1/comment")
@Api(value="Comment Resource Endpoint")
public class CommentController {

    @Autowired
    CommentService commentService;
    @Autowired
    PostService postService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping(path = "/{postId}")
    public ResponseEntity<?> createComment(@PathVariable("postId") Long postId,@RequestBody Comment comment, HttpSession session) {
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null) {
            throw new ResourceNotFoundException("No user found in the session please make sure you are logged in" );
        }
        Post post =  postService.getPostById(postId);
        comment.setPost(post);
        commentService.addComment(comment);
        return ResponseEntity.ok().build();
    }

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

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable (name = "commentId") Long commentId) {
        if(!commentService.checkExistence(commentId)){
            throw new ResourceNotFoundException("Post not found with id " + commentId);
        }
        commentService.delete(commentId);
        return ResponseEntity.ok().build();
    }


}

