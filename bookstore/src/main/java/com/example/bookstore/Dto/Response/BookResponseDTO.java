package com.example.bookstore.Dto.Response; // Ensure this package is correct

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {
    private Long id;
    private String title;
    private String isbn;
    private Double price;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;
    private int totalCopies;
    private int availableCopies;
    private Set<AuthorResponseDTO> authors;
}