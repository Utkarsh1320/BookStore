package com.example.bookstore.ServiceInterface;

import com.example.bookstore.Model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author saveAuthor(Author author);
    List<Author> getAllAuthor();
    Optional<Author> getAuthorById(Long id);
    Author updateAuthor(Long id, Author authorDetails);
    void deleteAuthor(Long id);
    List<Author> findByLastNameContainingIgnoreCase(String firstName);
    List<Author> findByFirstNameContainingIgnoreCase(String firstName);

}
