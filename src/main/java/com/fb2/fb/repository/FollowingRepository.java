package com.fb2.fb.repository;


import com.fb2.fb.model.Following;
import com.fb2.fb.model.Post;
import com.fb2.fb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowingRepository extends JpaRepository<Following, Long> {
    Following getFollowingByUser1AndUser2(User user1, User user2);
    Following getFollowingByUser1(User user1);


    @Query("SELECT p.user2 FROM Following p WHERE p.user1 = ?1")
    public List<User> following (User user1);

    @Query("SELECT p.user1 FROM Following p WHERE p.user2 = ?1")
    public List<User> followers (User user2);


}
