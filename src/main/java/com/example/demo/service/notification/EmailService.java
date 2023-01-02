package com.example.demo.service.notification;

import com.example.demo.dto.request.userRequest.MailRequest;
import com.example.demo.dto.response.userResponse.MailResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
    CompletableFuture<MailResponse> sendMail(MailRequest mailRequest) throws UnirestException;
}
