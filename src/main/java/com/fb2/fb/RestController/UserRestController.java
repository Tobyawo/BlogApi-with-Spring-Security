
package com.fb2.fb.RestController;

import com.fb2.fb.model.User;
import com.fb2.fb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping(path = "/api/v1/user")
public class UserRestController {

    UserService userService;

    @Autowired
    public UserRestController(UserService userService){
        this.userService = userService;
    }

    // new user login
    // to manipulate http response status we use the Response entity.
    // incase the value enteed  for id is not found or invalid
    @GetMapping(path = "/login/{email}/{password}", produces = "application/json")
    public ResponseEntity<User> loginUser(@PathVariable("email") String email, @PathVariable("password") String password, HttpSession httpSession){
        User user = userService.getUserByEmail(email);
        if( user== null){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        User user1 = userService.getUserByEmailAndPassword(email, password);
        if (user1 == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);}
        httpSession.setAttribute("user", user1);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    //new user signup
    @PostMapping(path = "/signUp/{firstname}/{lastname}/{email}/{password}", produces = "application/json")
    public ResponseEntity<User> signUpUser(@PathVariable("firstname") String firstname,@PathVariable("lastname") String lastname,@PathVariable("email") String email, @PathVariable("password") String password,HttpSession httpSession){
        User user = new User();
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        user.setPassword(password);
        userService.addUser(user);
        User user1 = userService.getUserByEmail(email);
        if( user1== null){
            return new ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }




}
