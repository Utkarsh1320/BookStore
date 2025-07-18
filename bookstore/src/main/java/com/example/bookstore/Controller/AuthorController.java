package com.example.bookstore.Controller;

import com.example.bookstore.Dto.AuthorRequestDTO;
import com.example.bookstore.Dto.AuthorResponseDTO;
import com.example.bookstore.Model.Author;
import com.example.bookstore.ServiceInterface.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    private AuthorResponseDTO convertDto(Author author){
        if(author == null){
            return null;
        }
        return new AuthorResponseDTO(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getBirthDate()
        );
    }
    private Author convertToEntity(@Valid AuthorRequestDTO authorRequestDTO){
        if(authorRequestDTO == null) return null;
        return new Author(
                null,
                authorRequestDTO.getFirstName(),
                authorRequestDTO.getLastName(),
                authorRequestDTO.getBirthDate()
        );
    }
    @PostMapping
    public ResponseEntity<AuthorResponseDTO> createAuthor(@Valid @RequestBody AuthorRequestDTO authorRequestDTO){
        Author authorToSave = convertToEntity(authorRequestDTO);
        Author savedAuthor = authorService.saveAuthor(authorToSave);
        return new ResponseEntity<>(convertDto(savedAuthor), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAllAuthors(@RequestParam(required = false) String firstName,@RequestParam(required = false) String lastName){
        List<Author> authors;
        if(firstName != null && !firstName.trim().isEmpty()){
            authors = authorService.findByFirstNameContainingIgnoreCase(firstName);
        }else if(lastName != null && !lastName.trim().isEmpty()){
            authors = authorService.findByLastNameContainingIgnoreCase(lastName);
        }else{
            authors = authorService.getAllAuthor();
        }
        List<AuthorResponseDTO> authorDTOs = authors.stream()
                .map(this :: convertDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(authorDTOs);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(@PathVariable Long id){
        Optional<Author> authorOptional = authorService.getAuthorById(id);
        if(authorOptional.isPresent()){
            return ResponseEntity.ok(convertDto(authorOptional.get()));
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> updateBook(@PathVariable Long id, @Valid @RequestBody AuthorRequestDTO authorRequestDTO){
        Author authorToUpdate = convertToEntity(authorRequestDTO);
        authorToUpdate.setId(id);

        Author updateAuthor = authorService.updateAuthor(id, authorToUpdate);
        if(updateAuthor != null){
            return  ResponseEntity.ok(convertDto(updateAuthor));
        }else {
            return  ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id){
        try{
            authorService.deleteAuthor(id);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }


}
