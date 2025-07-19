package com.example.bookstore.ServiceInterface;

import com.example.bookstore.Model.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    Book saveBook(Book book, Set<Long> authorIds);
    List<Book> getAllBooks();
    Optional<Book> getBookById(Long id);
    Book updateBook(Long id, Book bookDetails);
    void deleteBook(Long id);
    List<Book> searchBooksByTitle(String titleKeyword);
}
