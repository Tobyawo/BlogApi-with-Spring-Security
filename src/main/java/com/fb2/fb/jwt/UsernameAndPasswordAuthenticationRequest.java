package com.fb2.fb.jwt;

public class UsernameAndPasswordAuthenticationRequest {

    private String username;
    private String password;


    public UsernameAndPasswordAuthenticationRequest() {
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


