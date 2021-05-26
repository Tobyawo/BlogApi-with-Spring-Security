package com.fb2.fb.repository;

import com.fb2.fb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);
    Boolean findByEmail(String email);

    User getUserByEmailAndPassword(String email, String password);

    User getUserById(Long userId);


}
