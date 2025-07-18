package com.example.bookstore.Dto; // Ensure this package is correct

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class BookResponseDTO {
    private Long id;
    private String title;
    private String isbn;
    private Double price;
    private LocalDate publicationDate;


    public BookResponseDTO() {}


    public BookResponseDTO(Long id, String title, String isbn, Double price, LocalDate publicationDate) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.publicationDate = publicationDate;
    }

}