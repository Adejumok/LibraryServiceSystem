package com.example.demo.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterNewUserResponse {
    private String email;
    private String message;
}
