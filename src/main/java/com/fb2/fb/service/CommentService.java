package com.fb2.fb.service;

import com.fb2.fb.model.Comment;
import com.fb2.fb.model.Post;
import com.fb2.fb.model.User;
import com.fb2.fb.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public void addComment(Comment comment1) {
        comment1.setCreatedAt(getDate());
        commentRepository.save(comment1);
    }

    public void addComment2(User user, Post post, String comments) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setComment(comments);
        comment.setCreatedAt(getDate());
        commentRepository.save(comment);
    }




    public Comment getCommentById(Long commentId) {
        return commentRepository.getCommentById(commentId);
    }

    public void editComment(Comment comment, String comment1) {
        comment.setComment(comment1);
        commentRepository.save(comment);
    }



    public void updateComment(long commentId, String comments) {
        Comment comment = commentRepository.getCommentById(commentId);
        comment.setComment(comments);
        commentRepository.save(comment);
    }

    public void delete(long commentId) {
        commentRepository.deleteById(commentId);
    }

    public void delete2(long commentId) {
        //Comment comment = commentRepository.findByCommentId(commentId);
        commentRepository.deleteById(commentId);
    }




    public Boolean checkExistence(Long postId){
        if(commentRepository.existsById(postId)){
            return true;
        }
        return false;
    }

//    public Boolean checkExistence(Long commentId){
//        if(commentRepository.existsById(commentId)){
//            return true;
//        }
//        return false;
//    }


    public String getDate() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.format(DateTimeFormatter.ofPattern("hh:mm a | dd-MMM"));
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    public List<Comment> findAll(Long id) {
        return commentRepository.findAllByPostId(id);
    }



    public List<Comment> findAllByPost(Long id) {
        return commentRepository.findAllByPost(id);
    }








    public Comment get(long commentId) {
        return commentRepository.findById(commentId).get();
    }



    public void deleteComment(Long commentId) {
        try {
            //commentRepository.deleteCommentByCommentId(commentId);
            commentRepository.deleteById(commentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Comment getCommentById(long commentId) {
        return commentRepository.getCommentById(commentId);
    }



}
