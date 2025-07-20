package com.example.bookstore.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
