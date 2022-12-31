package com.example.demo.dto.request;

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
