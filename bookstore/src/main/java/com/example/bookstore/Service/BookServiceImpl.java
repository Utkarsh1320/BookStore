package com.example.bookstore.Service;

import com.example.bookstore.Model.Author;
import com.example.bookstore.Repository.AuthorRepository;
import com.example.bookstore.ServiceInterface.BookService;
import com.example.bookstore.Model.Book;
import com.example.bookstore.Repository.BookRepository;
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
        book.setAvailableCopies(book.getTotalCopies());
        if(authorIds != null && !authorIds.isEmpty()){
            Set<Author> authors = new HashSet<>();
            for(Long authorId : authorIds){
                authorRepository.findById(authorId)
                        .ifPresent(authors::add);
            }
            book.setAuthors(authors);
        }else{
            Set<Author> authors = new HashSet<>();
            book.setAuthors(authors);
        }
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book updateBook(Long id, Book bookDetails) {
        Optional<Book> exisitingBookOptional = bookRepository.findById(id);
        if(exisitingBookOptional.isPresent()){
            Book exisitingBook = exisitingBookOptional.get();
            exisitingBook.setTitle(bookDetails.getTitle());
            exisitingBook.setPrice(bookDetails.getPrice());
            exisitingBook.setIsbn(bookDetails.getIsbn());
            exisitingBook.setPublicationDate(bookDetails.getPublicationDate());
            return bookRepository.save(exisitingBook);
        }else {
            return null;
        }
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> searchBooksByTitle(String titleKeyword) {
        return bookRepository.findByTitleContainingIgnoreCase(titleKeyword);
    }

}
