package com.fb2.fb.Auth;

import com.fb2.fb.Auth.ApplicationUserDAO;
import com.fb2.fb.model.User;
import com.fb2.fb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ApplicationUserService implements UserDetailsService {

@Autowired
    UserRepository userRepository;
@Autowired
    public ApplicationUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
//    @Autowired
//    public ApplicationUserService(@Qualifier("fake") ApplicationUserDAO applicationUserDAO) {
//        this.applicationUserDAO = applicationUserDAO;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        user.orElseThrow(()-> new UsernameNotFoundException(String.format("Username %s Not Found", username)));
      return user.map(ApplicationUser::new).get();

//        return applicationUserDAO
//                .selectApplicationUserByUsername(username)
//                .orElseThrow(()-> new UsernameNotFoundException(String.format("Username %s Not Found", username)));
    }
}
