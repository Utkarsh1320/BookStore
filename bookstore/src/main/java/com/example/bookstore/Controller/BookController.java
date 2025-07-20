package com.example.bookstore.Controller;

import com.example.bookstore.Dto.Response.AuthorResponseDTO;
import com.example.bookstore.Dto.Request.BookRequestDTO;
import com.example.bookstore.Dto.Response.BookResponseDTO;
import com.example.bookstore.Model.Author;
import com.example.bookstore.Model.Book;
import com.example.bookstore.ServiceInterface.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;


    public BookController(BookService bookService){
        this.bookService = bookService;
    }
    private BookResponseDTO convertToDto(Book book){
        if(book == null){
            return null;
        }
        Set<AuthorResponseDTO> authorDTOs = book.getAuthors().stream()
                .map(this::convertToAuthorDto)
                .collect(Collectors.toSet());
        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getPrice(),
                book.getPublicationDate(),
                authorDTOs
        );
    }
    private AuthorResponseDTO convertToAuthorDto(Author author) {
        if(author == null) return null;
        return new AuthorResponseDTO(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getBirthDate()
        );
    }
    private Book convertToEntity(@Valid BookRequestDTO bookResponseDTO){
        if(bookResponseDTO == null) return null;
        return new Book(
                null,
                bookResponseDTO.getTitle(),
                bookResponseDTO.getIsbn(),
                bookResponseDTO.getPrice(),
                bookResponseDTO.getPublicationDate(),
                null
        );
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO bookRequestDTO){
        Book bookToSave = convertToEntity(bookRequestDTO);
        Book savedBook = bookService.saveBook(bookToSave, bookRequestDTO.getAuthorIds());
        return new ResponseEntity<>(convertToDto(savedBook), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks(@RequestParam(required = false) String title){
        List<Book> books;
        if(title != null && !title.trim().isEmpty()){
            books = bookService.searchBooksByTitle(title);
        }else {
            books = bookService.getAllBooks();
        }
        List<BookResponseDTO> bookDTOs = books.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookDTOs);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        Optional<Book> bookOptional = bookService.getBookById(id);
        if (bookOptional.isPresent()) {
            return ResponseEntity.ok(convertToDto(bookOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequestDTO bookRequestDTO) {
        Book bookToUpdate = convertToEntity(bookRequestDTO);
        bookToUpdate.setId(id);
        Book updatedBook = bookService.updateBook(id, bookToUpdate);
        if (updatedBook != null) {
            return ResponseEntity.ok(convertToDto(updatedBook));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
