package com.fb2.fb.repository;

import com.fb2.fb.model.Post;
import com.fb2.fb.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM posts p WHERE lower(p.title) LIKE %?1%" + " OR lower(p.message) LIKE %?1%" )
    public List<Post> search(String keyword);




    Post getPostById(Long id);


//    Post findById(Long id);

    List<Post> findAllByUser(User user);

//    @Query("SELECT p FROM posts p WHERE p.user =?1")
//    List<Post> findPostOfCurrentUSer(User user);









            List<Post> findPostByUser(User user);






}


