package com.example.demo.service;

import com.example.demo.dto.request.RegisterNewUserRequest;
import com.example.demo.dto.response.RegisterNewUserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface LibrarySystemUserService extends UserDetailsService {
    RegisterNewUserResponse registerNewUserResponse(RegisterNewUserRequest registerNewUserRequest);
}
