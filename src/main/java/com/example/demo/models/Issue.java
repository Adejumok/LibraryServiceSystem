package com.example.demo.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime issueDate;
    private LocalDateTime issueExpiry;
    private String description;
    @ManyToOne
    private LibrarySystemUser user;
    @ManyToOne
    private Book book;
}
