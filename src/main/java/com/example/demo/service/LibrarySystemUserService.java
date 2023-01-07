package com.example.demo.service;

import com.example.demo.dto.request.userRequest.RegisterNewUserRequest;
import com.example.demo.dto.request.userRequest.UserLoginRequest;
import com.example.demo.dto.request.userRequest.UserUpdateRequest;
import com.example.demo.dto.response.userResponse.RegisterNewUserResponse;
import com.example.demo.dto.response.userResponse.UserLoginResponse;
import com.example.demo.dto.response.userResponse.UserUpdateResponse;
import com.example.demo.models.LibrarySystemUser;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface LibrarySystemUserService extends UserDetailsService {
    RegisterNewUserResponse registerNewUserResponse(RegisterNewUserRequest registerNewUserRequest) throws UnirestException;
    UserUpdateResponse userUpdateResponse(UserUpdateRequest updateRequest);
    LibrarySystemUser getUserByEmail(String email);

    List<LibrarySystemUser> getAllUsers();

}
