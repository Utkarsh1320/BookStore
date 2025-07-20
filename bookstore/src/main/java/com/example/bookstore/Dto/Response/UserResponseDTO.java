package com.example.bookstore.Dto.Response;

import java.util.Set;

public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String username;
    private String password;
    private String email;

    private Set<LoanResponseDTO> loans;
}
