package com.fb2.fb.service;

import com.fb2.fb.model.User;
import com.fb2.fb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public void addUser(User user) {
        user.setCreatedAt(getDate());
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


    public Boolean checkUserExistenceById(Long userId){
        if(userRepository.existsById(userId)){
            return true;
        }
        return false;
    }


    public String getDate() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.format(DateTimeFormatter.ofPattern("hh:mm a | dd-MMM"));
    }

    public void delete(User user) {
        System.out.println("***** DELETE USER CALLED *****");
        SimpleDateFormat DateFor = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 3);
        String presentDate = DateFor.format(calendar.getTime());
        user.setIsDeactivated(true);
        user.setDeactivationDate(presentDate);
        userRepository.save(user);
    }

    public void undoDelete(User user) {
        System.err.println("***** UNDO DELETE CALLED *****");
        user.setIsDeactivated(false);
        user.setDeactivationDate(null);
        userRepository.save(user);
    }

    public void deactivateUserScheduler() {
        List<User> users = userRepository.findAllByIsDeactivated(true);
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        System.err.println("***** SCHEDULER IS RUNNING *****");
        users.forEach(user -> {
            String presentDate = DateFor.format(date);
            String deleteDate = user.getDeactivationDate();
            int deleteAction = presentDate.compareTo(deleteDate);
            if (deleteAction > 0 || deleteAction == 0) {
                user.setIsDeleted(true);
                userRepository.save(user);
            }
        });
    }




}
