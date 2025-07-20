package com.example.bookstore.Dto.Response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponseDTO {
    private Long id;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    private Long userId;
    private String userFirstName;

    private Long bookId;
    private String bookTitle;
}
