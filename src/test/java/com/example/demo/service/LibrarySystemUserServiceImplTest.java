package com.example.demo.service;

import com.example.demo.dto.request.RegisterNewUserRequest;
import com.example.demo.dto.request.UserLoginRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.RegisterNewUserResponse;
import com.example.demo.dto.response.UserLoginResponse;
import com.example.demo.dto.response.UserUpdateResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class LibrarySystemUserServiceImplTest {

    private static RegisterNewUserRequest request;
    private static UserLoginRequest loginRequest;
    private static UserUpdateRequest updateRequest;
    @Autowired
    private LibrarySystemUserService service;

    @BeforeAll
    static void setUp(){
        request = RegisterNewUserRequest.builder()
                .firstName("Kunle")
                .email("kay@gmail.com")
                .password("1234")
                .build();

        updateRequest = UserUpdateRequest.builder()
                .lastName("Quadri")
                .email("kay@gmail.com")
                .mobile("2348134567389")
                .address("No 2, Gbera Street, Eko")
                .build();
    }

    @Test
    void registerNewUserTest() {
        RegisterNewUserResponse response = service.registerNewUserResponse(request);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isNotNull();
        assertThat(response.getEmail()).isNotNull();
    }

    @Test
    void updateTest(){
        UserUpdateResponse updateResponse = service.userUpdateResponse(updateRequest);
        assertThat(updateResponse).isNotNull();
    }
}