package com.fb2.fb.service;

import com.fb2.fb.model.Comment;
import com.fb2.fb.model.CommentLike;
import com.fb2.fb.model.User;
import com.fb2.fb.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeService {
    @Autowired
    CommentLikeRepository commentLikeRepository;

    public CommentLike getCommentLikeByCommentAndUser(Comment comment, User userObj) {
        return commentLikeRepository.getCommentLikeByCommentAndUser(comment, userObj);
    }
//
//    public void addLike(CommentLike like) {
//        commentLikeRepository.save(like);
//    }
//
//
//    public void deleteLike(CommentLike like) {
//        commentLikeRepository.delete(like);
//    }


    public CommentLike getCommentLikeByPostAndUser(Comment comment, User user) {
        return commentLikeRepository.getCommentLikeByCommentAndUser(comment, user);
    }

//    public CommentLike getCommentLikeByCommentAndUser(Comment comment, User userObj) {
//        return commentLikeRepository.getCommentLikeByCommentAndUser(comment, userObj);
//    }

    public void addLike(CommentLike like) {
        commentLikeRepository.save(like);
    }


    public void deleteLike(CommentLike like) {
        commentLikeRepository.delete(like);
    }



}
