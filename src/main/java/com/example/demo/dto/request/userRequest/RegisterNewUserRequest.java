package com.example.demo.dto.request.userRequest;

import com.example.demo.models.Authority;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterNewUserRequest {
    private String email;
    private String firstName;
    private String password;
}
