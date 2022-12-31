package com.example.demo.controller;

import com.example.demo.dto.request.RegisterNewUserRequest;
import com.example.demo.dto.request.UserLoginRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.RegisterNewUserResponse;
import com.example.demo.dto.response.UserUpdateResponse;
import com.example.demo.exception.LibrarySystemException;
import com.example.demo.models.LibrarySystemUser;
import com.example.demo.security.AuthToken;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.service.LibrarySystemUserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
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
    private final AuthenticationManager authenticationManager;
    private final LibrarySystemUserService userService;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@NonNull @RequestBody RegisterNewUserRequest newUserRequest){
        RegisterNewUserResponse response = userService.registerNewUserResponse(newUserRequest);
        return new ResponseEntity<> (response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest) throws LibrarySystemException{
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        }catch (BadCredentialsException ex){
            throw new LibrarySystemException("Invalid login details.", 404);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateToken(authentication);
        LibrarySystemUser user = userService.getUserByEmail(loginRequest.getEmail());
        return new  ResponseEntity<>(new AuthToken(token, user.getId()), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserUpdateRequest updateRequest) throws LibrarySystemException{
        UserUpdateResponse response = userService.userUpdateResponse(updateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
