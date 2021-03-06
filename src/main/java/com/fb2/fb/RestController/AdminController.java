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
@RequestMapping(path = "/admin/")
public class AdminController {

    UserService userService;

    @Autowired
    public AdminController(UserService userService){
        this.userService = userService;
    }



    // only admin and admin trainees is allowed can get all blogposts
    @ApiOperation(value = "Get all users on the blog")
    @GetMapping(path = "users")

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")    //using annotations to defining getting authorities for admin and trainee
    @ResponseBody
    public Page<?> getAllBlogUserswithPagination(@PageableDefault(sort="id",direction = Sort.Direction.DESC, size=10) Pageable page){
        if(userService.getAllUser(page)==null){
            throw new ResourceNotFoundException("No user found on this blog" );
        }
        return userService.getAllUser(page);
    }




    //new user signup
    @GetMapping(path = "adminProfile", produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public ResponseEntity<String> checkAdminProfile(){
        System.out.println("Only admins can see admin profiles");
        return ResponseEntity.ok().build();
    }

    //new user signup
    @PostMapping(path = "addNewAdmin", produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public ResponseEntity<?> signUpUserANewAdmin(){
        System.out.println("Only admins can see list of admins");
        return ResponseEntity.ok().build();
    }





    // only admin can delete/deactivate an
    @ApiOperation(value = "Delete account")
    @DeleteMapping("deactivate/{userId}")
    @PreAuthorize("hasAuthority('admin:delete')")  //using annotations to defining delete authority for admin only
    public ResponseEntity<?> deactivateUserAccountByAdmin(@PathVariable(name="userId") Long userId) {
        userService.delete(userId);
        System.out.println(" only admin has authority to delete. not a trainee admin");
        return ResponseEntity.ok().build();
    }





}