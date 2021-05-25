package com.fb2.fb.RestController;

import com.fb2.fb.Exception.ResourceNotFoundException;
import com.fb2.fb.model.*;
import com.fb2.fb.service.CommentService;
import com.fb2.fb.service.FavoriteService;
import com.fb2.fb.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/favorites")
@Api(value="Post Resource Rest Endpoint")
public class FavoriteController {


    @Autowired
    FavoriteService favoriteService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;



    @PostMapping("post/{postId}")
    public ResponseEntity<?> addFavouritePost(@PathVariable("postId") Long postId,Favorites fav, HttpSession session) {
        User userObj = (User) session.getAttribute("user");
        if (userObj == null) {
            throw new ResourceNotFoundException("No User found with in the session");}
        if(!postService.checkExistence(postId)){
            throw new ResourceNotFoundException("Post not found with id " + postId);
        }
        Post post = postService.getPostById(postId);
        Favorites favorites = favoriteService.getFavoritesByTitleAndUser(post.getTitle(),userObj);
        fav.setPostTitle(post.getTitle());
        fav.setUser(userObj);
        fav.setFavoritePost(post.getMessage());
        if (favorites.getFavoritePost() != fav.getFavoritePost()) {
            favoriteService.addFavourite(fav);
            System.err.println("successfully added a favorite");}
        else{
            System.out.println("content and title already exist in your favourites");
            throw new ResourceNotFoundException("content and title already exist in your favourites");
        }
//        } else if(favorites != null && favStatus.equalsIgnoreCase("remove")){
//            favoriteService.deleteFavorite(fav);
//            System.err.println("successfully deleted a favourite");
//        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFavouritePost(@PathVariable("id") Long favoriteId, Favorites fav, HttpSession session) {
        User userObj = (User) session.getAttribute("user");
        if (userObj == null) {
            throw new ResourceNotFoundException("No User found with in the session");}
        if(!favoriteService.checkExistence(favoriteId)){
            throw new ResourceNotFoundException("favourite not found with id " + favoriteId);
        }
            favoriteService.deleteFavoriteById(favoriteId);
            System.err.println("successfully deleted a favourite");
         return ResponseEntity.ok().build();
    }


    @PostMapping("comment/{commentId}/")
    public ResponseEntity<?> addFavouriteComment(@PathVariable("commentId") Long commentId,Favorites fav, HttpSession session) {
        User userObj = (User) session.getAttribute("user");
        if (userObj == null) {
            throw new ResourceNotFoundException("No User found with in the session");}
        if(!commentService.checkExistence(commentId)){
            throw new ResourceNotFoundException("Post not found with id " + commentId);
        }
        Comment comment = commentService.getCommentById(commentId);
        Post post = comment.getPost();
        fav.setPostTitle(post.getTitle());
        fav.setUser(userObj);
        fav.setFavoritePost(comment.getComment());
        Favorites favorites = favoriteService.getFavoritesByTitleAndUser(post.getTitle(), userObj);
        if (favorites.getFavoritePost() != fav.getFavoritePost()) {
            favoriteService.addFavourite(fav);
            System.err.println("successfully added a favorite");
        }
        else{

            System.out.println("content and title already exist in your favourites");
            throw new ResourceNotFoundException("content and title already exist in your favourites");
        }
        return ResponseEntity.ok().build();
    }

//    @DeleteMapping("comment/{commentId}/")
//    public ResponseEntity<?> deleteFavouriteComment(@PathVariable("commentId") Long commentId, Favorites fav, HttpSession session) {
//        User userObj = (User) session.getAttribute("user");
//        if (userObj == null) {
//            throw new ResourceNotFoundException("No User found with in the session");}
//        if(!commentService.checkExistence(commentId)){
//            throw new ResourceNotFoundException("Post not found with id " + commentId);
//        }
//        Comment comment = commentService.getCommentById(commentId);
//        Post post = comment.getPost();
//        fav.setPostTitle(post.getTitle());
//        fav.setUser(userObj);
//        fav.setFavoritePost(comment.getComment());
//        Favorites favorites = favoriteService.getFavoritesByTitleAndUser(post.getTitle(), userObj);
//        if(favorites != null){
//            favoriteService.deleteFavorite(fav);
//            System.err.println("successfully deleted a favourite");
//        }
//        return ResponseEntity.ok().build();
//    }





    @ApiOperation(value = "Get all favourites of the user")
    @GetMapping(path = "/", produces = "application/json")
    @ResponseBody // Springboot uses this to auto convert  our response to Json or Xml format
    public List<Favorites> getAllUserFavorites(HttpSession session){
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null) {
            throw new ResourceNotFoundException("No user found in the session please make sure you are logged in" );}
//        checkExistence
        if(favoriteService.findAllFavoritesByUser(authenticatedUser).size()==0){
            throw new ResourceNotFoundException("No favorites found for the user" );
        }
        List<Favorites> favoritesList = favoriteService.findAllFavoritesByUser(authenticatedUser);

        System.err.println("listof favorites by user are " + favoritesList);
        return favoritesList;
    }





}



