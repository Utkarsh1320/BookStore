package com.example.bookstore.Service;

import com.example.bookstore.Model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(Book book);
    List<Book> getAllBooks();
    Optional<Book> getBookById(Long id);
    Book updateBook(Long id, Book bookDetails);
    void deleteBook(Long id);
    List<Book> searchBooksByTitle(String titleKeyword);
}
