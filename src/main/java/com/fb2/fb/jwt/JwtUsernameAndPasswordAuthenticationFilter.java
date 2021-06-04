package com.fb2.fb.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;


    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, SecretKey secretKey, JwtConfig jwtConfig) {
        this.authenticationManager = authenticationManager;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }


    // this method validates clients credentials received
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        System.err.println("do you get to jwt username" + "authtype = "+request.getAuthType()+"username = " +request.getParameter("userName")+"password = "+request.getParameter("password"));
try{
    UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
            .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
    Authentication authentication= new UsernamePasswordAuthenticationToken(
            authenticationRequest.getUserName(),
            authenticationRequest.getPassword());//the username is the principal and the password is the credentials for the authentication request
    System.err.println(authenticationRequest.getUserName() +" "+   authenticationRequest.getPassword());
    Authentication authenticate = authenticationManager.authenticate(authentication);
    System.err.println("authenticatea " + authenticate);
    return authenticate;
}
catch (IOException e){throw new RuntimeException(e);}

    }



    // this method will be invoked after the authentication is successful.
    // it creates a jwt token and send to the client
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())  // gets the name of the authentication result
                .claim("authorities",authResult.getAuthorities())  // this gets the body of the jwt token
                .setIssuedAt(new java.util.Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))  // we set the token to expire in two weeks
                .signWith(secretKey) // jwt signature on the token..make sure the key word is long and could not be easily guessed
                .compact();
        System.err.println("token generated = "+ token);
       // sending the token generated to client. so the client can use it for every request
        response.addHeader(jwtConfig.getAuthorizationHeader(),jwtConfig.getTokenPrefix()+ token);
    }
}
