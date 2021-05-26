package com.fb2.fb.service;

import com.fb2.fb.model.Post;
import com.fb2.fb.model.User;
import com.fb2.fb.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;


    public Iterable<Post> getAllPost() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public void addPost(Post post, User user) {
        post.setUser(user);
        postRepository.save(post);
    }

    public Post getPostById(Long id) {
        return postRepository.getPostById(id);
    }

    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    public void delete(long postId) {
        postRepository.deleteById(postId);
    }

    public void editPost(Post post, String message) {
        post.setMessage(message);
        postRepository.save(post);
    }

    public Boolean checkExistence(Long postId){
        if(postRepository.existsById(postId)){
            return true;
        }
        return false;
    }


    public List<Post> searchAll(String keyword) {
        if (keyword != null) {
            return postRepository.search(keyword.toLowerCase(Locale.ROOT));
        }
        return postRepository.findAll();
    }


}
