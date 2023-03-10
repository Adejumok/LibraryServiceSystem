package com.example.demo.controller;

import com.example.demo.dto.request.userRequest.RegisterNewUserRequest;
import com.example.demo.dto.request.userRequest.UserLoginRequest;
import com.example.demo.dto.request.userRequest.UserUpdateRequest;
import com.example.demo.dto.response.userResponse.RegisterNewUserResponse;
import com.example.demo.dto.response.userResponse.UserUpdateResponse;
import com.example.demo.exception.LibrarySystemException;
import com.example.demo.models.LibrarySystemUser;
import com.example.demo.security.AuthToken;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.service.LibrarySystemUserService;
import com.mashape.unirest.http.exceptions.UnirestException;
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

import java.util.List;
@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class LibraryUserController {
    private final AuthenticationManager authenticationManager;
    private final LibrarySystemUserService userService;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@NonNull @RequestBody RegisterNewUserRequest newUserRequest) throws UnirestException {
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

    @PatchMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserUpdateRequest updateRequest) throws LibrarySystemException{
        UserUpdateResponse response = userService.userUpdateResponse(updateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public List<LibrarySystemUser> librarySystemUsers(){
        return userService.getAllUsers();
    }
}
