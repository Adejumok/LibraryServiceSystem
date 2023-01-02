package com.example.demo.dto.request.userRequest;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest {
    private String lastName;
    private String email;
    private String mobile;
    private String address;
}
