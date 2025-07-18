package com.example.bookstore.Repository;

import com.example.bookstore.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByLastNameContainingIgnoreCase(String lastName);
    List<Author> findByFirstNameContainingIgnoreCase(String firstName);
}
