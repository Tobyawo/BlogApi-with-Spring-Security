package com.fb2.fb.repository;

import com.fb2.fb.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Transactional
    Comment getCommentById(Long commentId);

    List<Comment> findAllByPostId(Long id);

void deleteById(Long commentId);
//   void deleteCommentByCommentId(Long commentId);
//    Comment findByCommentId(Long commentId);

//    Comment getCommentByCommentId(Long commentId);

    List<Comment> findAllByPost(Long postId);

}
