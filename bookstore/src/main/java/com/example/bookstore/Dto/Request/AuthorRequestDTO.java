package com.example.bookstore.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequestDTO {
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Last name cannot exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    @Size(max = 100, message = "Last name cannot exceed 100 characters")
    private String lastName;

    @PastOrPresent(message = "Birthdate cannot be in future")
    private LocalDate birthDate;
}
