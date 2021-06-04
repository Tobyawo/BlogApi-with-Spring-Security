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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path = "/api/v1/")
public class secureUserController {

    UserService userService;

    @Autowired
    public secureUserController(UserService userService){
        this.userService = userService;
    }



    // only admin and admin trainees is allowed can get all blogposts
    @ApiOperation(value = "Get all users on the blog")
    @GetMapping(path = "users")

    @ResponseBody
    public Page<?> getAllBlogUserswithPagination(@PageableDefault(sort="id",direction = Sort.Direction.DESC, size=10) Pageable page){
        if(userService.getAllUser(page)==null){
            throw new ResourceNotFoundException("No user found on this blog" );
        }
        return userService.getAllUser(page);
    }




    //new user signup
    @GetMapping(path = "view1", produces = "application/json")
    public ResponseEntity<String> checkAdminProfile(){
        System.out.println("anyone can come here to see view1");
        return ResponseEntity.ok().build();
    }

    //new user signup
    @PostMapping(path = "view2", produces = "application/json")
    public ResponseEntity<?> signUpUserANewAdmin(){
        System.out.println("anyone can come to view 2");
        return ResponseEntity.ok().build();
    }











}