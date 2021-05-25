package com.fb2.fb.service;

import com.fb2.fb.model.Post;
import com.fb2.fb.model.PostLike;
import com.fb2.fb.model.User;
import com.fb2.fb.repository.PostLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostLikeService {
    @Autowired
    PostLikeRepository postLikeRepository;

    public PostLike getPostLikeByPostAndUser(Post post, User user) {
        return postLikeRepository.getPostLikeByPostAndAndUser(post, user);
    }

    public void addLike(PostLike like) {
        postLikeRepository.save(like);
    }

    public void deleteLike(PostLike like) {
        postLikeRepository.delete(like);
    }

    public void deleteAllPostLike(Post post, User user) {
        postLikeRepository.deleteAllByPostAndUser(post, user);
    }
}
