package com.example.demo.dto.request;

import com.example.demo.models.enums.BookType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IssueBookRequest {
    private LocalDateTime issueDate;
    private LocalDateTime issueExpiry;
    private Long issueId;
    private Long isbn;
    private String userEmail;
}
