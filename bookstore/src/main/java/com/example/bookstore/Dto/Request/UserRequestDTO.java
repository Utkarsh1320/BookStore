package com.example.bookstore.Dto.Request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "First Name cannot be blank")
    @Size(max = 30, message = "First Name cannot exceed 30 characters")
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    @Size(max = 40, message = "Last Name cannot exceed 40 characters")
    private String lastName;

    @Size(max = 255, message = "Address cannot exceed the 255 characters")
    private String address;

    @NotBlank(message = "username Cannot be blank")
    @Size(min = 5, max = 26, message = "number of username characters are not valid")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Email
    private String email;
}
