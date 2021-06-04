package com.fb2.fb.service;

import com.fb2.fb.model.User;
import com.fb2.fb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

@Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }




//    public void addUser(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setCreatedAt(getDate());
//        userRepository.save(user);
//    }
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(getDate());
        userRepository.save(user);
    }


    public User getUserByEmailAndPassword(String email, String password) {
        User user = userRepository.getUserByEmailAndPassword(email, passwordEncoder.encode(password));
        return  user;
    }

//    public User getUserByEmailAndPassword(String email, String password) {
//        User user = userRepository.getUserByEmailAndPassword(email, passwordEncoder.encode(password));
//        return  user;
//    }



    public User getUserById(Long userId) {
        return userRepository.getUserById(userId);
    }

    public List<User> AllUser() {
        return userRepository.findAll();
    }
    public Page<User> getAllUser(Pageable page) {
        return userRepository.findAll(page);
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




    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(getDate());
        userRepository.save(user);
    }

    public User get(long id){
        return userRepository.findById(id).get();}



    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> getUserById(long userid) {
        Optional<User> newuser = userRepository.findById(userid);
        return newuser;
    }




}
