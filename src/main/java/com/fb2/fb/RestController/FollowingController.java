package com.fb2.fb.RestController;


import com.fb2.fb.Exception.ResourceNotFoundException;
import com.fb2.fb.model.Favorites;
import com.fb2.fb.model.Following;
import com.fb2.fb.model.Post;
import com.fb2.fb.model.User;
import com.fb2.fb.service.FollowingService;
import com.fb2.fb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "/api/v1/following")
public class FollowingController {

    @Autowired
    UserService userService;

    @Autowired
    FollowingService followingService;



    @PostMapping("/user/{userId}")
    public ResponseEntity<?> followUser(@PathVariable("userId") Long userId,Following foll, HttpSession session) {
        User userObj = (User) session.getAttribute("user");
        if (userObj == null) {
            throw new ResourceNotFoundException("No User found with in the session");}
        if(!userService.checkUserExistenceById(userId)){
            throw new ResourceNotFoundException("User not found with id " + userId); }
        User user2 = userService.getUserById(userId);
        foll.setUser1(userObj);
        foll.setUser2(user2);
        Following followingCheck = followingService.getFollowingByUser1AndUser2(userObj,user2);
       if (followingCheck==null && !(foll.getUser1().getEmail().equalsIgnoreCase(foll.getUser2().getEmail()))){
            followingService.addFollowing(foll);
            System.err.println("successfully follow a favorite user");}
        else if(followingCheck==null && (foll.getUser1().getEmail().equalsIgnoreCase(foll.getUser2().getEmail()))){
           throw new ResourceNotFoundException("Cannot establish connection with urself");}
          else if(followingCheck!=null){
            throw new ResourceNotFoundException("Connection with the user already established");
        }
        return ResponseEntity.ok().build();
    }



    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> UnFollow(@PathVariable("userId") Long userId, Favorites fav, HttpSession session) {
        User userObj = (User) session.getAttribute("user");
        User user2 = userService.getUserById(userId);
        if (userObj == null) {
            throw new ResourceNotFoundException("No User found with in the session");}
        if(!followingService.checkExistenceOfConnection(userObj,user2)){
            throw new ResourceNotFoundException("No connections found between you and the user");
        }
        Following followingCheck = followingService.getFollowingByUser1AndUser2(userObj,user2);
        followingService.deleteFollowing(followingCheck);
        System.err.println("successfully unfollow a user");
        return ResponseEntity.ok().build();
    }


}
