package com.fb2.fb;


import com.fb2.fb.model.Post;
import com.fb2.fb.model.User;
import com.fb2.fb.repository.PostRepository;
import com.fb2.fb.repository.UserRepository;
import com.fb2.fb.service.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

@Autowired
    private PostService postService;

@MockBean
    private PostRepository postRepository;

@Test
public void getAllPosts(){
//mocking a user
User user = new User(1l,"oluwa","tobi","tobi@mail.com","1234");
// mocking a database for the repository
when(postRepository.findAll()).thenReturn(Stream.of(
        new Post(1l,user,"first title","tobi first post","04:53 PM | 28-May"),
        new Post (2l,user,"second title","tobi second post","04:53 PM | 28-May"),
        new Post(3l,user,"third title","tobi third post","04:53 PM | 28-May")).collect(Collectors.toList()));
  //assertion
    assertEquals(3,postService.getListofPost().size());
}




    @Test
    public void getAllPostsById(){
    Long postId = 2l;
        User user = new User(1l,"oluwa","tobi","tobi@mail.com","1234");
    Post post = new Post (2l,user,"second title","tobi second post","04:53 PM | 28-May");

        // if the post with id 2 in the repository is given as post above
        when(postRepository.getPostById(postId)).thenReturn(post);

        assertEquals(post,postService.getPostById(postId));

    }

    @Test
    public void getSavePost(){
        User user = new User(4l,"blessing","olatunji","blessing@mail.com","1234");
        Post post = new Post (3l,user,"third title"," third post","04:53 PM | 28-May");
        postService.addPost(post,user);
        //check how many times the repo method save was called
        verify(postRepository,times(1)).save(post);

    }



    @Test
    public void deletePost(){
        User user = new User(4l,"blessing","olatunji","blessing@mail.com","1234");
        Post post = new Post (3l,user,"third title"," third post","04:53 PM | 28-May");
        postService.delete(post.getId());
        //check how many times the repo method deletebyId was called
        verify(postRepository,times(1)).deleteById(post.getId());


    }



}
