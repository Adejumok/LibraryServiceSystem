package com.example.demo.dto.response.bookResponse;

import com.example.demo.models.enums.BookType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddBookResponse {
    private String title;
    private String author;
    private BookType bookType;
    private Long isbn;
    private String message;
}
