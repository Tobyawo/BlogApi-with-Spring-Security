package com.fb2.fb.repository;

import com.fb2.fb.model.Post;
import com.fb2.fb.model.PostLike;
import com.fb2.fb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    PostLike getPostLikeByPostAndAndUser(Post post, User user);

    void deleteAllByPostAndUser(Post post, User user);
}
