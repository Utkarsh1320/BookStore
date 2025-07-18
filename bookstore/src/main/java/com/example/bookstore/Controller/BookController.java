package com.example.bookstore.Controller;

import com.example.bookstore.Dto.AuthorResponseDTO; // NEW import for DTO conversion
import com.example.bookstore.Dto.BookRequestDTO;
import com.example.bookstore.Dto.BookResponseDTO;
import com.example.bookstore.Model.Author; // NEW import for entity conversion
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

        // Convert the Set<Author> to a Set<AuthorResponseDTO>
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

    private Book convertToEntity(@Valid BookRequestDTO bookRequestDTO){
        if(bookRequestDTO == null) return null;
        return new Book(
                null,
                bookRequestDTO.getTitle(),
                bookRequestDTO.getIsbn(),
                bookRequestDTO.getPrice(),
                bookRequestDTO.getPublicationDate(),
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

        // UPDATED: Use the updated convertToDto method
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
        Book updatedBook = bookService.updateBook(id, bookToUpdate, bookRequestDTO.getAuthorIds()); // CRITICAL CHANGE
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