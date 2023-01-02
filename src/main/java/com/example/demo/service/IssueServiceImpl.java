package com.example.demo.service;

import com.example.demo.dto.request.IssueBookRequest;
import com.example.demo.dto.response.IssueBookResponse;
import com.example.demo.models.Book;
import com.example.demo.models.Issue;
import com.example.demo.models.LibrarySystemUser;
import com.example.demo.repositories.IssueRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
 class IssueServiceImpl implements IssueService{
    private final LibrarySystemUserService librarySystemUserService;
    private final BookService bookService;
    private IssueRepository issueRepository;

    @Override
    public IssueBookResponse issueBookResponse(IssueBookRequest bookRequest) {
        LibrarySystemUser systemUser = librarySystemUserService.getUserByEmail(bookRequest.getUserEmail());
        Book book = bookService.getABook(bookRequest.getIsbn());
        Issue issue = Issue.builder()
                .book(book)
                .user(systemUser)
                .issueDate(LocalDateTime.now())
                .issueExpiry(LocalDateTime.now().plusDays(10))
                .build();
        issueRepository.save(issue);
        IssueBookResponse response = IssueBookResponse.builder()
                .message("Book with isbn "+bookRequest.getIsbn()+" successfully issued " +
                        "to "+systemUser.getFirstName())
                .build();
        return response;
    }
}
