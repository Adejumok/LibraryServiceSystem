package com.example.demo.repositories;

import com.example.demo.models.LibrarySystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<LibrarySystemUser, Long> {
    Optional<LibrarySystemUser> findByEmail(String email);
}
