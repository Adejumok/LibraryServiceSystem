package com.example.demo.service;

import com.example.demo.dto.request.IssueBookRequest;
import com.example.demo.dto.response.IssueBookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class IssueServiceTest {
    @Autowired
    private IssueService service;
    private IssueBookRequest bookRequest;

    @BeforeEach
    void setUp(){
        bookRequest = IssueBookRequest.builder()
                .userEmail("m@gmail.com")
                .isbn(132875939L)
                .build();
    }
    @Test
    void issueBookTest(){
        IssueBookResponse response = service.issueBookResponse(bookRequest);
        assertThat(response).isNotNull();
    }
}
