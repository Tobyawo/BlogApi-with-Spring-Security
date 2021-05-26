package com.fb2.fb.service;

import com.fb2.fb.model.User;
import com.fb2.fb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User getUserByEmailAndPassword(String email, String password) {
        return userRepository.getUserByEmailAndPassword(email, password);
    }
    public User getUserById(Long userId) {
        return userRepository.getUserById(userId);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public Boolean checkUserExistenceByEmail(String email){
        if(userRepository.findByEmail(email)){
            return true;
        }
        return false;
    }

    public Boolean checkUserExistenceById(Long userId){
        if(userRepository.existsById(userId)){
            return true;
        }
        return false;
    }
}
