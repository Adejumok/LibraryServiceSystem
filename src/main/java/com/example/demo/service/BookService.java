package com.example.demo.service;

import com.example.demo.dto.request.bookRequest.AddBookRequest;
import com.example.demo.dto.request.bookRequest.EditBookRequest;
import com.example.demo.dto.response.bookResponse.AddBookResponse;
import com.example.demo.dto.response.bookResponse.EditBookResponse;
import com.example.demo.models.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    AddBookResponse addBookResponse(AddBookRequest addBookRequest) throws IOException;

    void removeBook(long l);

    Book getABook(long l);

    List<Book> getAllBooks();

    EditBookResponse editBookResponse(EditBookRequest editBookRequest);
}
