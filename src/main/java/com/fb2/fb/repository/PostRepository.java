package com.fb2.fb.repository;

import com.fb2.fb.model.Post;
import com.fb2.fb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post getPostById(Long id);
    List<Post> findPostByUser(User user);

}
