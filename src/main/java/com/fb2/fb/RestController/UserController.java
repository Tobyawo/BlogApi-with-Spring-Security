
package com.fb2.fb.RestController;

import com.fb2.fb.Exception.ResourceNotFoundException;
import com.fb2.fb.model.Post;
import com.fb2.fb.model.User;
import com.fb2.fb.model.request.LoginRequest;
import com.fb2.fb.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    // new user login
    // to manipulate http response status we use the Response entity.
    // incase the value enteed  for id is not found or invalid
    @PostMapping(path = "user/login", produces = "application/json")
    public ResponseEntity<User> loginUser(@RequestBody LoginRequest loginRequest, HttpSession httpSession){
        User user = userService.getUserByEmail(loginRequest.getEmail());
        if( user== null){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        User user1 = userService.getUserByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        if (user1 == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);}
        httpSession.setAttribute("user", user1);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }




    //new user signup
    @PostMapping(path = "/signUp", produces = "application/json")
    public ResponseEntity<User> signUpUser( @RequestBody User user){
        userService.addUser(user);
        User user1 = userService.getUserByEmail(user.getEmail());
        if( user1== null){
            return new ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }











    @ApiOperation(value = "Get all users on the blog")
    @GetMapping(path = "/All",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_ATOM_XML_VALUE})
    @ResponseBody // Springboot uses this to auto convert  our response to Json or Xml format
    public List<?> getAllBlogUsers(HttpSession session){
        User authenticatedUser = (User) session.getAttribute("user");
//        if (authenticatedUser == null) {
//            throw new ResourceNotFoundException("No user found in the session please make sure you are logged in" );}
        if(userService.getAllUser()==null){
            throw new ResourceNotFoundException("No user found on this blog" );
        }

        List<User> userList = userService.getAllUser();
        return userList;
    }




//    @ApiOperation(value = "Delete account")
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<?> deactivateUserAccount(@PathVariable("userId") Long userId,HttpSession session) {
//        User userObj = (User) session.getAttribute("user");
//        if (userObj == null) {
//            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);}
//        userService.;
//        return ResponseEntity.ok().build();
//    }


}
