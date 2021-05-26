package com.fb2.fb.model.request;

import lombok.Data;

import javax.persistence.Column;

@Data
public class LoginRequest {
    @Column(unique = true, nullable = false)
    private String email;


    @Column(nullable = false)
    private String password;
}