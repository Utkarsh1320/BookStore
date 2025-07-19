package com.example.bookstore.Service;

import com.example.bookstore.Model.Book;
import com.example.bookstore.Repository.BookRepository;
import com.example.bookstore.Service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public Book saveBook(Book book){
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
