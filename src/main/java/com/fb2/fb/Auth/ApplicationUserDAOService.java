//package com.fb2.fb.Auth;
//
//import com.google.common.collect.Lists;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Repository;
//
//import static com.fb2.fb.Security.ApplicationUserRole.*;
//import java.util.List;
//import java.util.Optional;
//
//
//@Repository("fake") //we just fake this repository. instead of connecting to a real database. Qualifier name was used incase we have many repo with different implementation
//public class ApplicationUserDAOService implements ApplicationUserDAO{
//
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public ApplicationUserDAOService(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
//        return getApplicationUser().stream()
//                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
//                .findFirst();
//    }
//
//    private List<ApplicationUser> getApplicationUser(){
//        List<ApplicationUser> applicationUser = Lists.newArrayList(
//new ApplicationUser(
//        "tobyawo",
//        passwordEncoder.encode("password"),
//        USER.getGrantedAuthority(),
//        true,
//        true,
//        true,
//        true),
//
//                new ApplicationUser(
//                        "tbawoyomi@gmail.com",
//                        passwordEncoder.encode("12345"),
//                        USER.getGrantedAuthority(),
//                        true,
//                        true,
//                        true,
//                        true),
//
//
//                new ApplicationUser(
//                        "tom",
//                        passwordEncoder.encode("password123"),
//                        ADMINTRAINEE.getGrantedAuthority(),
//                        true,
//                        true,
//                        true,
//                        true),
//
//                new ApplicationUser(
//                        "admin",
//                        passwordEncoder.encode("password123"),
//                        ADMIN.getGrantedAuthority(),
//                        true,
//                        true,
//                        true,
//                        true)
//        )
//                ;return applicationUser;
//    }
//
//}
