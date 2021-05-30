package com.fb2.fb.RestController;

import com.fb2.fb.Exception.ResourceNotFoundException;
import com.fb2.fb.model.Post;
import com.fb2.fb.model.User;
import com.fb2.fb.model.request.LoginRequest;
import com.fb2.fb.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }



    @GetMapping(path = "/login", produces = "application/json")
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





//allowed for only admin
    @ApiOperation(value = "Get all users on the blog")
    @GetMapping(path = "/admin/users",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_ATOM_XML_VALUE})
    @ResponseBody
    public Page<?> getAllBlogUserswithPagination(@PageableDefault(sort="id",direction = Sort.Direction.DESC, size=10) Pageable page){
        if(userService.getAllUser(page)==null){
            throw new ResourceNotFoundException("No user found on this blog" );
        }
        return userService.getAllUser(page);
    }




    @ApiOperation(value = "Delete account")
    @DeleteMapping("/user/deactivate")
    public ResponseEntity<?> deactivateUserAccount(HttpSession session) {
        User userObj = (User) session.getAttribute("user");
        if (userObj == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);}
        userService.delete(userObj);
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "cancel deletion of account")
    @DeleteMapping("/user/cancelDelete")
    public ResponseEntity<?> cancelDeactivationofUserAccount(HttpSession session) {
        User userObj = (User) session.getAttribute("user");
        if (userObj == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);}
        userService.undoDelete(userObj);
        return ResponseEntity.ok().build();
    }


}