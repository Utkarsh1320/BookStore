package com.example.bookstore.Dto.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDTO {
    @NotBlank(message = "Title cannot be empty")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "ISBN cannot be empty")
    @Pattern(regexp = "^[0-9Xx-]{10,17}$", message = "Invalid ISBN format")
    private String isbn;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Publication date cannot be null")
    @PastOrPresent(message = "Publication date cannot be in the future")
    private LocalDate publicationDate;

    private Set<Long> authorIds;
}
