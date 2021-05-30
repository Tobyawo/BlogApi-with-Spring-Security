package com.fb2.fb.RestController;


import com.fb2.fb.Constants.AppConstants;
import com.fb2.fb.Exception.ResourceNotFoundException;
import com.fb2.fb.model.Comment;
import com.fb2.fb.model.Post;
import com.fb2.fb.model.User;
import com.fb2.fb.model.request.PagedResponse;
import com.fb2.fb.repository.CommentRepository;
import com.fb2.fb.repository.FavoritesRepository;
import com.fb2.fb.repository.FollowingRepository;
import com.fb2.fb.repository.PostRepository;
import com.fb2.fb.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/")
@Api(value="Post Resource Rest Endpoint")
public class PostController {
    PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    FollowingService followingService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    FavoritesRepository favoritesRepository;

    @Autowired
    FollowingRepository followingRepository;



    @ApiOperation(value = "Adds new Post")
    @ApiResponses(value= {@ApiResponse(code = 100,message = "i will check the message for 100 code error later"),
            @ApiResponse(code = 100,message = "i will check the message for 100 code error later"),
            @ApiResponse(code = 200,message = "ok..Good Job"),
            @ApiResponse(code = 404,message = "Why resources not found"),
            @ApiResponse(code = 500,message = "What the hell is wrong with my server")})
    @PostMapping(path = "post/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    @ResponseBody
    public ResponseEntity<?> newPost( @RequestBody Post post,HttpSession session) {

        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null) {
            return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
        }
        post.setUser(authenticatedUser);
        System.err.println(post);
        System.err.println(authenticatedUser);
        postService.addPost(post, authenticatedUser);
        return new ResponseEntity< >(post, HttpStatus.OK);
    }


    @ApiOperation(value = "Get all posts of the user")
    @GetMapping(path = "post/posts", produces = "application/json")
    @ResponseBody
    public List<Post> getAllUserPost(HttpSession session){
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null) {
            throw new ResourceNotFoundException("No user found in the session please make sure you are logged in" );}
        if(postRepository.findPostByUser(authenticatedUser)==null){
            throw new ResourceNotFoundException("No post found for the user" );
        }
     List<Post> postList = postRepository.findPostByUser(authenticatedUser);
        System.out.println("user in session is "+ authenticatedUser);
        System.err.println("listof posts by users are " + postList);
        return postList;
    }

    @ApiOperation(value = "Get all posts of the user's connections")
    @GetMapping(path = "post/connectionsPosts", produces = "application/json")
    @ResponseBody
    public List<Post> getAllUsersConnectionPost(HttpSession session){
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null) {
            throw new ResourceNotFoundException("No user found in the session please make sure you are logged in" );}
        List<User> myConnectedUsers = followingService.usersConnections(authenticatedUser);
        if(myConnectedUsers==null){
            throw new ResourceNotFoundException("You have no connection" );}
       List<Post> connectionsPosts = new ArrayList<>();
        for (User user:myConnectedUsers) {
            List<Post> userpost = postRepository.findPostByUser(user);
            connectionsPosts.addAll(userpost);
        }
        if(connectionsPosts==null){
            throw new ResourceNotFoundException("No Post found for all your connections" );
        }
        return  connectionsPosts;
    }


//Role base Authentication: allowed for only admin

    @ApiOperation(value = "Get all posts on the blog")
    @GetMapping("admin/BlogPosts")
    public ResponseEntity<PagedResponse<Post>> getAllPosts(
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        PagedResponse<Post> response = postService.getAllPosts(page, size);
        return new ResponseEntity< >(response, HttpStatus.OK); }


    @ApiOperation(value = "Get post by the post ID")
    @GetMapping(path = "post/{postId}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Post> getPostById(@PathVariable("postId")long postId, HttpSession session){
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null) {
            throw new ResourceNotFoundException("No user found in the session please make sure you are logged in" );}
        if(!postService.checkExistence(postId)){
            throw new ResourceNotFoundException("Post not found with id " + postId);
        }
        Post post = postService.getPostById(postId);
        return new ResponseEntity< >(post, HttpStatus.OK);
    }

    @ApiOperation(value = "Get all comments by the post ID")
    @GetMapping(path = "post/{postId}/comments", produces = "application/json")
    @ResponseBody
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
    @PutMapping("post/{postId}/")
    public ResponseEntity<Post> updatePost(@PathVariable(name ="postId")long postId,@RequestBody Post post,  HttpSession session) {
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null) {
            throw new ResourceNotFoundException("No user found in the session please make sure you are logged in" );}
        if(!postService.checkExistence(postId)){
            throw new ResourceNotFoundException("Post not found with id " + postId);
        }
        Post post1 = postService.getPostById(postId);
        post1.setMessage(post.getMessage());
        post1.setTitle(post.getTitle());
        postService.addPost(post1,authenticatedUser);
        return new ResponseEntity< >(post, HttpStatus.OK);
    }



    @ApiOperation(value = "Delete post by the post ID")
    @DeleteMapping("post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        if(!postService.checkExistence(postId)){
            throw new ResourceNotFoundException("Post not found with id " + postId);
        }
        postService.delete(postId);
        return ResponseEntity.ok().build();
    }


}










    //

//    @GetMapping(path = "/BlogPosts",
//            produces = {MediaType.APPLICATION_JSON_VALUE,
//                    MediaType.APPLICATION_ATOM_XML_VALUE})
//    @ResponseBody // Springboot uses this to auto convert  our response to Json or Xml format
//    public List<Post> getAllBlogPost(HttpSession session){
//        User authenticatedUser = (User) session.getAttribute("user");
////        if (authenticatedUser == null) {
////            throw new ResourceNotFoundException("No user found in the session please make sure you are logged in" );}
//        if(postService.getAllPost()==null){
//            throw new ResourceNotFoundException("No post found on this blog" );
//        }
//
//        List<Post> postList = (List<Post>) postService.getAllPost();
//        return  postList;
//    }


