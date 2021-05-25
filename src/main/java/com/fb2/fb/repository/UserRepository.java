package com.fb2.fb.repository;

import com.fb2.fb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);

    User getUserByEmailAndPassword(String email, String password);
}
