package com.example.demo.dto.response;

import com.example.demo.models.enums.BookType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IssueBookResponse {
    private Long issueId;
    private Long isbn;
    private String userEmail;
    private String message;
}
