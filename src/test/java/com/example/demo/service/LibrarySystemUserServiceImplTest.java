package com.example.demo.service;

import com.cloudinary.Cloudinary;
import com.example.demo.dto.request.IssueBookRequest;
import com.example.demo.dto.request.userRequest.MailRequest;
import com.example.demo.dto.request.userRequest.RegisterNewUserRequest;
import com.example.demo.dto.request.userRequest.UserLoginRequest;
import com.example.demo.dto.request.userRequest.UserUpdateRequest;
import com.example.demo.dto.response.IssueBookResponse;
import com.example.demo.dto.response.userResponse.RegisterNewUserResponse;
import com.example.demo.dto.response.userResponse.UserUpdateResponse;
import com.example.demo.service.cloud.CloudService;
import com.example.demo.service.notification.EmailService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;

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
                .firstName("Akin")
                .email("akin@gmail.com")
                .password("1234")
                .build();

        updateRequest = UserUpdateRequest.builder()
                .lastName("Moses")
                .email("akin@gmail.com")
                .mobile("2348134567389")
                .address("No 2, Gbera Street, Eko")
                .build();
    }

    @Test
    void registerNewUserTest() throws UnirestException {
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