package com.example.bookstore.Controller;

import com.example.bookstore.Model.Book;
import com.example.bookstore.Repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        Book savedBook = bookRepository.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> allBooks = bookRepository.findAll();
        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Optional<Book> book = bookRepository.findById(id);
        return book.map(ResponseEntity :: ok).orElseGet(() -> ResponseEntity.notFound().build());
//        if(book.isPresent()){
//            return ResponseEntity.ok(book.get());
//        }else{
//            return ResponseEntity.notFound().build();
//        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails){
        Optional<Book> exisitingBookOptional = bookRepository.findById(id);
        if(exisitingBookOptional.isPresent()){
            Book exisitingBook = exisitingBookOptional.get();
            exisitingBook.setTitle(bookDetails.getTitle());
            exisitingBook.setIsbn(bookDetails.getIsbn());
            exisitingBook.setPrice(bookDetails.getPrice());
            exisitingBook.setPublicationDate(bookDetails.getPublicationDate());

            Book updatedBook = bookRepository.save(exisitingBook);
            return ResponseEntity.ok(updatedBook);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
