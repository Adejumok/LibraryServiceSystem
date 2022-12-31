package com.example.demo.service.notification;

import com.example.demo.dto.request.MailRequest;
import com.example.demo.dto.response.MailResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
    CompletableFuture<MailResponse> sendMail(MailRequest mailRequest) throws UnirestException;
}
