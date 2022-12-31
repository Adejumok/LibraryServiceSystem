package com.example.demo.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateResponse {
    private String email;
    private String message;
}
