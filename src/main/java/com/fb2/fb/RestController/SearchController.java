package com.fb2.fb.RestController;


import com.fb2.fb.model.Post;
import com.fb2.fb.repository.CommentRepository;
import com.fb2.fb.repository.PostRepository;
import com.fb2.fb.service.CommentService;
import com.fb2.fb.service.FollowingService;
import com.fb2.fb.service.PostService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/search")
@Api(value="Search Resource Rest Endpoint")
public class SearchController {

    @Autowired

    PostService postService;


    @GetMapping("/{keyword}")
    public ResponseEntity<List<Post>> searchForPost(@PathVariable(name ="keyword") String keyword) {
        List listPost = postService.searchAll(keyword);
        return new ResponseEntity<List<Post>>(listPost, HttpStatus.OK);

    }
}
