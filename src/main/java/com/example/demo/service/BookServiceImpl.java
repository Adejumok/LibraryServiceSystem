package com.example.demo.service;

import com.cloudinary.utils.ObjectUtils;
import com.example.demo.dto.request.AddBookRequest;
import com.example.demo.dto.request.EditBookRequest;
import com.example.demo.dto.response.AddBookResponse;
import com.example.demo.dto.response.EditBookResponse;
import com.example.demo.exception.LibrarySystemException;
import com.example.demo.models.Book;
import com.example.demo.repositories.BookRepository;
import com.example.demo.service.cloud.CloudService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    private final CloudService cloudService;
    private final ModelMapper mapper;
    @Override
    public AddBookResponse addBookResponse(AddBookRequest addBookRequest) throws IOException {
        Optional<Book> foundBook = bookRepository.findByIsbn(addBookRequest.getIsbn());
        if (foundBook.isPresent()){
            throw new LibrarySystemException("Book with "+addBookRequest.getIsbn()+" " +
                    "already added exist.", 400);
        }
        Book book = mapper.map(addBookRequest, Book.class);
        String imageUrl = cloudService.upload(addBookRequest.getImage()
                .getBytes(), ObjectUtils.emptyMap());
        book.setImageUrl(imageUrl);
        bookRepository.save(book);
        AddBookResponse response = new AddBookResponse();
        BeanUtils.copyProperties(book, response);
        response.setMessage(book.getTitle()+" successfully added.");
        return response;
    }

    @Override
    public void removeBook(long isbn) {
        Optional<Book> foundBook = bookRepository.findByIsbn(isbn);
        if (foundBook.isEmpty()){
            throw new LibrarySystemException("The book with this isbn does not exist.", 404);
        }
        bookRepository.delete(foundBook.get());
    }

    @Override
    public Book getABook(long isbn) {
        Optional<Book> foundBook = bookRepository.findByIsbn(isbn);
        if (foundBook.isEmpty()){
            throw new LibrarySystemException("The book with this isbn does not exist.", 404);
        }
        return foundBook.get();
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public EditBookResponse editBookResponse(EditBookRequest editBookRequest) {
        Book gottenBook = getABook(editBookRequest.getIsbn());
        if (gottenBook == null){
            throw new LibrarySystemException("Book with isbn "+editBookRequest.getIsbn()+"" +
                    "not found",404);
        }
        gottenBook.setTitle(editBookRequest.getTitle());
        gottenBook.setAuthor(editBookRequest.getAuthor());
        gottenBook.setBookDescription(editBookRequest.getBookDescription());
        gottenBook.setImageUrl(editBookRequest.getImageUrl());
        gottenBook.setBookType(editBookRequest.getBookType());
        bookRepository.save(gottenBook);
        EditBookResponse response = new EditBookResponse();
        BeanUtils.copyProperties(gottenBook, response);
        response.setMessage("Both with isbn "+editBookRequest.getIsbn()+"" +
                "successfully updated.");
        return response;
    }
}
