package com.fb2.fb.RestController;


import com.fb2.fb.Exception.ResourceNotFoundException;
import com.fb2.fb.model.Post;
import com.fb2.fb.model.PostLike;
import com.fb2.fb.model.User;
import com.fb2.fb.repository.CommentRepository;
import com.fb2.fb.repository.PostRepository;
import com.fb2.fb.service.CommentService;
import com.fb2.fb.service.PostLikeService;
import com.fb2.fb.service.PostService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "/api/v1/postLike")
@Api(value="Post Resource Rest Endpoint")
public class PostLikeRestController {


    @Autowired
    PostService postService;

    @Autowired
    PostLikeService likeService;


    @PostMapping("/{postId}/{Like}")
    public ResponseEntity<?> likeIndex(@PathVariable("postId") Long postId,@PathVariable("Like") String LikeStatus, HttpSession session, PostLike like) {
        User userObj = (User) session.getAttribute("user");
        if (userObj == null) {
            throw new ResourceNotFoundException("No User found with in the session");}
            if(!postService.checkExistence(postId)){
                throw new ResourceNotFoundException("Post not found with id " + postId);
            }
            Post post = postService.getPostById(postId);
            PostLike postLike = likeService.getPostLikeByPostAndUser(post, userObj);
            like.setPost(post);
            like.setUser(userObj);
            if (postLike == null && LikeStatus.equalsIgnoreCase("Like")) {
                likeService.addLike(like);
                System.err.println("successfully like a post");
            } else if(postLike != null && LikeStatus.equalsIgnoreCase("UnLike")){
                likeService.deleteLike(postLike);
                System.err.println("successfully Unlike a post");
            }

         return ResponseEntity.ok().build();
    }



}
