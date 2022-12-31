package com.example.demo.service;

import com.example.demo.dto.request.AddBookRequest;
import com.example.demo.dto.request.EditBookRequest;
import com.example.demo.dto.response.AddBookResponse;
import com.example.demo.dto.response.EditBookResponse;
import com.example.demo.models.Book;
import com.example.demo.models.enums.BookType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class BookServiceTest {
    private static AddBookRequest addBookRequest;
    private static AddBookRequest addBookRequest1;
    @Autowired
    private BookService bookService;
    private EditBookRequest editBookRequest;

    @BeforeEach
    void setUp()  {
        addBookRequest = AddBookRequest.builder()
                .title("Dee is a wonder woman")
                .author("Dorcas")
                .bookType(BookType.CLASSICS)
                .isbn(124577899L)
                .build();

        addBookRequest1 = AddBookRequest.builder()
                .title("Tomorrow is Christmas")
                .author("Jummie")
                .bookType(BookType.FANTASY)
                .isbn(132875939L)
                .build();

        editBookRequest = EditBookRequest.builder()
                .title("Tomorrow is a New Year")
                .author("Jummie")
                .bookType(BookType.FANTASY)
                .isbn(132875939L)
                .build();


    }


    @Test
    void addBookTest() throws IOException {
        AddBookResponse response = bookService.addBookResponse(addBookRequest);
        AddBookResponse response1 = bookService.addBookResponse(addBookRequest1);
        assertThat(response).isNotNull();
        assertThat(response1).isNotNull();
        assertThat(response.getMessage()).isNotNull();
        assertThat(response1.getMessage()).isNotNull();
    }

    @Test
    void getBookTest(){
        Book book = bookService.getABook(124577899L);
        assertThat(book.getId()).isGreaterThan(0);
        assertThat(book).isNotNull();
    }

    @Test
    void  editBookTest(){
        EditBookResponse response = bookService.editBookResponse(editBookRequest);
        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("Tomorrow is a New Year");
    }


    @Test
    void getAllBooksTest(){
        log.info("The list of books are --> {}", bookService.getAllBooks());

    }

    @Test
    void deleteBookTest(){
        bookService.removeBook(124577899L);
    }


}
