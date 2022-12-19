package com.example.demo.controller;

import com.example.demo.dto.request.RegisterNewUserRequest;
import com.example.demo.dto.request.UserLoginRequest;
import com.example.demo.dto.response.RegisterNewUserResponse;
import com.example.demo.exception.LibrarySystemException;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.LibrarySystemUserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class LibraryUserController {
    @Autowired
    private final AuthenticationManager authenticationManager;
    private final LibrarySystemUserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@NonNull @RequestBody RegisterNewUserRequest newUserRequest){
        RegisterNewUserResponse response = userService.registerNewUserResponse(newUserRequest);
        return new ResponseEntity<> (response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest) throws Exception{
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        }catch (BadCredentialsException ex){
            throw new LibrarySystemException("Invalid login details.", 404);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new  ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ROOT')")
    public String getBoard(){
        return "Welcome to the Dashboard. ";
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('ROOT')")
    public String getProfile(){
        return "Welcome to the Profile Page.";
    }
}
