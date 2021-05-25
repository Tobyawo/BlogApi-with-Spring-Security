package com.fb2.fb.RestController;


import com.fb2.fb.Exception.ResourceNotFoundException;
import com.fb2.fb.model.Comment;
import com.fb2.fb.model.Post;
import com.fb2.fb.model.User;
import com.fb2.fb.repository.CommentRepository;
import com.fb2.fb.repository.FavoritesRepository;
import com.fb2.fb.repository.PostRepository;
import com.fb2.fb.service.CommentService;
import com.fb2.fb.service.FavoriteService;
import com.fb2.fb.service.PostService;
import com.fb2.fb.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/v1/post")
@Api(value="Post Resource Rest Endpoint")
public class PostRestController {
    UserService userService;
    PostService postService;
    CommentService commentService;
    FavoriteService favoriteService;

    @Autowired
    public PostRestController(PostService postService){
        this.postService = postService;
    }
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    FavoritesRepository favoritesRepository;

    //the user make a post working
    @ApiOperation(value = "Adds new Post")
    @ApiResponses(value= {@ApiResponse(code = 100,message = "i will check the message for 100 code error later"),
            @ApiResponse(code = 100,message = "i will check the message for 100 code error later"),
            @ApiResponse(code = 200,message = "ok..Good Job"),
            @ApiResponse(code = 404,message = "Why resources not found"),
            @ApiResponse(code = 500,message = "What the hell is wrong with my server")})
    @PostMapping(path = "/{Title}/{postContent}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    @ResponseBody
    public ResponseEntity<?> newPost(@PathVariable("Title") String title,@PathVariable("postContent") String postContent, HttpSession session) {

        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null) {
            return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
        }
        Post post = new Post();
        post.setTitle(title);
        post.setUser(authenticatedUser);
        post.setMessage(postContent);
        System.err.println(post);
        System.err.println(authenticatedUser);
        postService.addPost(post, authenticatedUser);
        Post post1 = postService.getPostById(post.getId());
        if (post1 == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }


    @ApiOperation(value = "Get all posts of the user")
    @GetMapping(path = "/posts", produces = "application/json")
    @ResponseBody // Springboot uses this to auto convert  our response to Json or Xml format
    public List<Post> getAllUserPost(HttpSession session){
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null) {
            throw new ResourceNotFoundException("No user found in the session please make sure you are logged in" );}
        if(postRepository.findPostByUser(authenticatedUser)==null){
            throw new ResourceNotFoundException("No post found for the user" );
        }
//        List<Post> postList = new ArrayList<>();
     List<Post> postList = postRepository.findPostByUser(authenticatedUser);

        System.err.println("listof posts by users are " + postList);
        return postList;
    }


    //
    @ApiOperation(value = "Get all posts on the blog")
    @GetMapping(path = "/BlogPosts",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_ATOM_XML_VALUE})
    @ResponseBody // Springboot uses this to auto convert  our response to Json or Xml format
    public List<?> getAllBlogPost(HttpSession session){
        User authenticatedUser = (User) session.getAttribute("user");
//        if (authenticatedUser == null) {
//            throw new ResourceNotFoundException("No user found in the session please make sure you are logged in" );}
        if(postService.getAllPost()==null){
            throw new ResourceNotFoundException("No post found on this blog" );
        }

        List<Post> postList = (List<Post>) postService.getAllPost();
        return postList;
    }

//    @GetMapping("/BlogPosts2")
//    public Page<Post> getPosts(Pageable pageable) {
//        return postRepository.findAll(pageable);
//    }



    @ApiOperation(value = "Get post by the post ID")
    @GetMapping(path = "/{postId}", produces = "application/json")
    @ResponseBody
    public Post getPostById(@PathVariable("postId")long postId, HttpSession session){
        User authenticatedUser = (User) session.getAttribute("user");
//        if (authenticatedUser == null) {
//            throw new ResourceNotFoundException("No user found in the session please make sure you are logged in" );}
        if(!postService.checkExistence(postId)){
            throw new ResourceNotFoundException("Post not found with id " + postId);
        }


        Post post = postService.getPostById(postId);

        System.err.println(post);
        return post;
//        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @ApiOperation(value = "Get all comments by the post ID")
    @GetMapping(path = "/{postId}/comments", produces = "application/json")
    @ResponseBody // Springboot uses this to auto convert  our response to Json or Xml format
    public List<String> getAllCommentsPostById(@PathVariable("postId")long postId, HttpSession session){
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null) {
            throw new ResourceNotFoundException("No user found in the session please make sure you are logged in" );}
        if(!postService.checkExistence(postId)){
            throw new ResourceNotFoundException("Post not found with id " + postId);
        }
        Post post = postService.getPostById(postId);
        List<Comment> listcomments = post.getComments();
        System.err.println(listcomments);
        List<String> commentString = new ArrayList<>();
        if(listcomments.size()==0){
            throw new ResourceNotFoundException("No comment found with post id " + postId);
        }
        for (Comment comm:listcomments) {
            commentString.add("comment id: " + " " + comm.getId()+ ", " + authenticatedUser.getFirstName() + " " + authenticatedUser.getLastName() + "'s comment on this post: " + comm.getComment());
        }

        return commentString;

    }


    @ApiOperation(value = "Updates post content")
    @PutMapping("/{postId}/{postContent}")
    public ResponseEntity<Post> updatePost(@PathVariable(name ="postId")long postId,@PathVariable("postContent") String postContent,  HttpSession session) {
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null) {
            throw new ResourceNotFoundException("No user found in the session please make sure you are logged in" );}
        if(!postService.checkExistence(postId)){
            throw new ResourceNotFoundException("Post not found with id " + postId);
        }
        Post post = postService.getPostById(postId);
        post.setMessage(postContent);
        postService.addPost(post,authenticatedUser);
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete post by the post ID")
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        if(!postService.checkExistence(postId)){
            throw new ResourceNotFoundException("Post not found with id " + postId);
        }
        postService.delete(postId);
        return ResponseEntity.ok().build();
    }

//    	<div class="sidebar-widget"><h2 class="widgettitle">Search</h2>
//<form role="search" method="get" id="searchform" class="searchform" action="https://seths.blog/">
//				<div>
//					<label class="screen-reader-text" for="s">Search for:</label>
//					<input type="text" value="starting and finishing" name="s" id="s" />
//					<input type="submit" id="searchsubmit" value="" />
//				</div>
//			</form></div>
}

