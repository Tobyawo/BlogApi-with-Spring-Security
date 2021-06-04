package com.fb2.fb.Auth;

import com.fb2.fb.Auth.ApplicationUser;

import java.util.Optional;

public interface ApplicationUserDAO {
     Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}


