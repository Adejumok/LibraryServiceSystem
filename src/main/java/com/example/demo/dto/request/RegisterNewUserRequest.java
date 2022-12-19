package com.example.demo.dto.request;

import com.example.demo.models.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterNewUserRequest {
    private String email;
    private String firstName;
    private String password;
    private Authority authority;
}
