package com.example.demo.controller;

import com.example.demo.dto.request.bookRequest.AddBookRequest;
import com.example.demo.dto.response.bookResponse.AddBookResponse;
import com.example.demo.models.Book;
import com.example.demo.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class LibraryBookController {
    private final BookService bookService;
    @PostMapping("/addBook")
    public ResponseEntity<?> add(@RequestBody AddBookRequest request) throws IOException {
        AddBookResponse response = bookService.addBookResponse(request);
        return new  ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/getBook/{isbn}")
    public Book getBook(@PathVariable Long isbn){
        return bookService.getABook(isbn);
    }

    @DeleteMapping("/deleteBook{isbn}")
    public void deleteBook(@PathVariable Long isbn){
        bookService.removeBook(isbn);
    }
}
