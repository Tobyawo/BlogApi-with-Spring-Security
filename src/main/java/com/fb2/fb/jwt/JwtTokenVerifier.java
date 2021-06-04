package com.fb2.fb.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

@Autowired
    public JwtTokenVerifier(JwtConfig jwtConfig, SecretKey secretKey) {
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    // we want this filter to be executed once per request
    // sometimes filter can be invoked more than once
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {



// get the token fron the request header
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader()); // extract the authorization saved inside the header
        System.err.println(authorizationHeader);
        if(Strings.isNullOrEmpty(authorizationHeader)|| !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {// strings from com.google.com.base
 filterChain.doFilter(request,response);
 return;
}
String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), ""); // trying to remove the word Bearer from the token by replacing with empty space.
        System.err.println(token);
        try{

    JwtParser jwtParser = Jwts.parserBuilder()
            .setSigningKey(secretKey)
           .build();

    Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);



Claims body = claimsJws.getBody();
String username = body.getSubject();
var authorities =(List<Map<String,String>>) body.get("authorities");

Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream().map(m -> new SimpleGrantedAuthority(m.get("authority")))
        .collect(Collectors.toSet());

//
Authentication authentication = new UsernamePasswordAuthenticationToken(
            username, null, simpleGrantedAuthorities
    );
// the client is now authenticated
            SecurityContextHolder.getContext().setAuthentication(authentication);
}catch (JwtException e){
    throw new IllegalStateException(String.format("Token %s cannot be trusted",token));
}
filterChain.doFilter(request,response);
    }
}
