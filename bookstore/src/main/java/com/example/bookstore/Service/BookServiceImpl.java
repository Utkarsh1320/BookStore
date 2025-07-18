package com.example.bookstore.Service;

import com.example.bookstore.Model.Author;
import com.example.bookstore.Model.Book;
import com.example.bookstore.Repository.AuthorRepository;
import com.example.bookstore.Repository.BookRepository;
import com.example.bookstore.ServiceInterface.BookService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public Book saveBook(Book book, Set<Long> authorIds){
        Set<Author> authors = findAuthorsByIds(authorIds);
        book.setAuthors(authors);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional
    public Book updateBook(Long id, Book bookDetails, Set<Long> authorIds) {
        Optional<Book> existingBookOptional = bookRepository.findById(id);
        if(existingBookOptional.isPresent()){
            Book existingBook = existingBookOptional.get();
            existingBook.setTitle(bookDetails.getTitle());
            existingBook.setPrice(bookDetails.getPrice());
            existingBook.setIsbn(bookDetails.getIsbn());
            existingBook.setPublicationDate(bookDetails.getPublicationDate());

            Set<Author> authors = findAuthorsByIds(authorIds);
            existingBook.setAuthors(authors);

            return bookRepository.save(existingBook);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> searchBooksByTitle(String titleKeyword) {
        return bookRepository.findByTitleContainingIgnoreCase(titleKeyword);
    }

    private Set<Author> findAuthorsByIds(Set<Long> authorIds){
        if(authorIds == null || authorIds.isEmpty()){
            return new HashSet<>();
        }
        List<Author> authors = authorRepository.findAllById(authorIds);
//        if (authors.size() != authorIds.size()) {
//
//        }
        return new HashSet<>(authors);
    }
}