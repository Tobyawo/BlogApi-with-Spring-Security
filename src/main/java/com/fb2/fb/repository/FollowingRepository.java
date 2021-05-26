package com.fb2.fb.repository;


import com.fb2.fb.model.Following;
import com.fb2.fb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowingRepository extends JpaRepository<Following, Long> {
    Following getFollowingByUser1AndUser2(User user1, User user2);
    Following getFollowingByUser1(User user1);
//    Boolean findByUser1AndUser2(User user1, User user2);


//    PostLike getPostLikeByPostAndAndUser(Post post, User user);
//
//    void deleteAllByPostAndUser(Post post, User user);


}
