package com.example.demo.dto.request;

import com.example.demo.models.enums.BookType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditBookRequest {
    private String title;
    private String author;
    private BookType bookType;
    private Long isbn;
    private String bookDescription;
    private BigDecimal price;
    private String imageUrl;
}
