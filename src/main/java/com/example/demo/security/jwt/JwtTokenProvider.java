package com.example.demo.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JwtTokenProvider {
    String getUsername (String token);
    Date getExpirationDate (String token);
    <T> T getClaim(String token, Function<Claims, T> claimsResolver);
    Header<?> getHeader(String token);
    Claims getAllClaims(String token);
    Boolean isTokenExpired(String token);
    String generateToken(Authentication authentication);
    Boolean validateToken(String token, UserDetails userDetails);
    String generateTokenForVerification(String id);
    UsernamePasswordAuthenticationToken getAuthenticationToken(
            final String token, final Authentication existingAuth, final UserDetails userDetails);
}
