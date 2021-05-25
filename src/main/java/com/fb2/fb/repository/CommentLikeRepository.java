package com.fb2.fb.repository;

import com.fb2.fb.model.Comment;
import com.fb2.fb.model.CommentLike;
import com.fb2.fb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    CommentLike getCommentLikeByCommentAndUser(Comment comment, User userObj);
}
