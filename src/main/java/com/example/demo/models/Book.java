package com.example.demo.models;

import com.example.demo.models.enums.BookType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private LibrarySystemUser user;
    private String title;
    private String author;
    private BookType bookType;
    private LocalDate publicationDate;
    private BigDecimal price;
    private String bookDescription;
}
