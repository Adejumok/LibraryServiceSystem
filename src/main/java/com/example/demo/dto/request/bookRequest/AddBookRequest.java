package com.example.demo.dto.request.bookRequest;

import com.example.demo.models.enums.BookType;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddBookRequest {
    private String title;
    private String author;
    private BookType bookType;
    private Long isbn;
    private String image;
}
