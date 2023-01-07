package com.example.demo.dto.response.userResponse;

import com.example.demo.security.AuthToken;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponse {
    private AuthToken authToken;
}
