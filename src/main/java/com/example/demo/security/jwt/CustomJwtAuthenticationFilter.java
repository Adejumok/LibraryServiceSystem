package com.example.demo.security.jwt;

import com.example.demo.exception.LibrarySystemException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String username = null;
        String authToken = null;
        log.info("Authorized");

        if (header != null && header.startsWith("Bearer")){
            authToken = header.replace("Bearer", "");
            username = getUsername(authToken);
        }else {
            logger.warn("Couldn't find bearer string header," +
                    "will be ignored");
        }
        if (username != null && SecurityContextHolder.getContext()
                .getAuthentication() ==null){
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails == null){
                    throw new LibrarySystemException("User not found", 403);
                }
                log.info("User details --> {}", userDetails);
                if (tokenProvider.validateToken(authToken, userDetails)){
                    setAuthentication(request, username, authToken, userDetails);
                }
            }catch (LibrarySystemException e){
                log.info("User not found");
                response.sendError(e.getStatusCode(), e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(HttpServletRequest request, String username, String authToken, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication =
                tokenProvider.getAuthenticationToken(authToken,
                        SecurityContextHolder.getContext().getAuthentication(),
                        userDetails);

        authentication.setDetails(new WebAuthenticationDetailsSource()
                .buildDetails(request));
        logger.info("authenticated user "+ username +" setting security context");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getUsername(String authToken) {
        String username;
        try {
            username = tokenProvider.getUsername(authToken);
        }catch (IllegalArgumentException e){
            logger.error("An error has occurred while fetching username from token", e);
            throw new IllegalArgumentException(e.getMessage());
        }catch (ExpiredJwtException e){
            logger.warn("The token has expired", e);
            throw new JwtException(e.getMessage());
        }
        return username;
    }
}
