package com.fb2.fb.service;

import com.fb2.fb.Constants.AppConstants;
import com.fb2.fb.Exception.BadRequestException;
import com.fb2.fb.model.Post;
import com.fb2.fb.model.User;
import com.fb2.fb.model.request.PagedResponse;
import com.fb2.fb.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.fb2.fb.Constants.AppConstants.CREATED_AT;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;


    public Iterable<Post> getAllPost() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<Post> getListofPost() {
        return postRepository.findAll();
    }

    public void addPost(Post post, User user) {
        post.setCreatedAt(getDate());
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
        post.setCreatedAt(getDate());
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




    public String getDate() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.format(DateTimeFormatter.ofPattern("hh:mm a | dd-MMM"));
    }



    public PagedResponse<Post> getAllPosts(int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();

        return new PagedResponse<>(content, posts.getNumber(), posts.getSize(), posts.getTotalElements(),
                posts.getTotalPages(), posts.isLast());
    }


    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size < 0) {
            throw new BadRequestException("Size number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

}
