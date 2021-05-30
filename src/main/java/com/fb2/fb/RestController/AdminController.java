//package com.fb2.fb.RestController;
//
//
//import com.fb2.fb.Exception.ResourceNotFoundException;
//import com.fb2.fb.service.UserService;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(path = "admin/api/v1/")
//public class AdminController {
//
//    UserService userService;
//
//    @Autowired
//    public AdminController(UserService userService){
//        this.userService = userService;
//    }
//
//
//    @ApiOperation(value = "Get all users on the blog")
//    @GetMapping(path = "/users",
//            produces = {MediaType.APPLICATION_JSON_VALUE,
//                    MediaType.APPLICATION_ATOM_XML_VALUE})
//    @ResponseBody
//    public Page<?> getAllBlogUserswithPagination(@PageableDefault(sort="id",direction = Sort.Direction.DESC, size=10) Pageable page){
//        if(userService.getAllUser(page)==null){
//            throw new ResourceNotFoundException("No user found on this blog" );
//        }
//        return userService.getAllUser(page);
//    }
//
//
//
//
//}
