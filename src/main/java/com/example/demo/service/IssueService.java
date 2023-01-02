package com.example.demo.service;

import com.example.demo.dto.request.IssueBookRequest;
import com.example.demo.dto.response.IssueBookResponse;

public interface IssueService {
    IssueBookResponse issueBookResponse(IssueBookRequest bookRequest);
}
