package com.fb2.fb.service;


import com.fb2.fb.model.Favorites;
import com.fb2.fb.model.Following;
import com.fb2.fb.model.User;
import com.fb2.fb.repository.FollowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowingService {
    @Autowired
    FollowingRepository followingRepository;



    public void addFollowing(Following following) {
        followingRepository.save(following);
    }

    public void deleteFollowing(Following following) {
        followingRepository.delete(following);
    }

    public Following getFollowingByUser1AndUser2(User user1, User user2) {
        return followingRepository.getFollowingByUser1AndUser2(user1, user2);
    }

    public Boolean checkExistenceOfConnection(User user1, User user2){
        Following following = followingRepository.getFollowingByUser1AndUser2(user1,user2);
        if(following.getId()>0){return true;}
        return false;
    }


    public List<User> usersConnections (User user){
        List<User> connection1 = followingRepository.followers(user);
        if(connection1==null){
            System.err.println("you have no followers");
        }
        System.err.println(connection1);
        List<User> following = followingRepository.following(user);
        if(following==null){
            System.err.println("you have no following");
        }
        connection1.addAll(following);
        return connection1;

    }



    public void deleteFollowingById(Long id) {
        followingRepository.deleteById(id);
    }



}
