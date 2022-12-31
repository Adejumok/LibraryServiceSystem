package com.example.demo.service;

import com.example.demo.dto.request.RegisterNewUserRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.RegisterNewUserResponse;
import com.example.demo.dto.response.UserUpdateResponse;
import com.example.demo.models.LibrarySystemUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface LibrarySystemUserService extends UserDetailsService {
    RegisterNewUserResponse registerNewUserResponse(RegisterNewUserRequest registerNewUserRequest);

    UserUpdateResponse userUpdateResponse(UserUpdateRequest updateRequest);
    LibrarySystemUser getUserByEmail(String email);
}
