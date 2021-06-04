package com.fb2.fb.Auth;

import com.fb2.fb.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationUser implements UserDetails {


    private final String firstNname;
    private final String lastName;
    private final String email;
    private final String userName;
    private final String password;
    private Boolean isDeleted;
    private final List<GrantedAuthority> authorities;



// convert user details from the database into userDetails interface instance
    public ApplicationUser(User user){
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.firstNname = user.getFirstName();
        this.lastName = user.getLastName();
        this.isDeleted= user.getIsDeleted();
        this.authorities = Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
