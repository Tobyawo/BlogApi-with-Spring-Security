package com.fb2.fb.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.fb2.fb.Security.ApplicationUserRole.ADMIN;
import static com.fb2.fb.Security.ApplicationUserRole.USER;


//using Basic Auth
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    //every request must be authenticated i.e the client must specify the username and password
    //one of the drawback of basic auth is that you cant logout
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
              .antMatchers("/","index","/css/*","/js/*") //Whitelisting url: all the pages with any of these url path are excluded from requiring access.
              .permitAll()
              .antMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
              .anyRequest()
              .authenticated()
              .and()
              .httpBasic();
    }


//
// Note: roles is a high level view of all the users u have in ur application
//    within a role , we have different kind of permissions/authority
    // you can assign more than one role to a user
    @Override
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails tobyawoUser = User.builder()
                .username("tobyawo")
                .password(passwordEncoder.encode("password"))
                .roles(USER.name())  //ROLE_USER .
                .build();

        UserDetails tobyUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password123"))
                .roles(ADMIN.name())  //ROLE_ADMIN .
                .build();




return  new InMemoryUserDetailsManager(
        tobyawoUser, tobyUser
);
    }



}
