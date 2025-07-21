package com.example.bookstore.Controller;

import com.example.bookstore.Dto.Request.UserRequestDTO;
import com.example.bookstore.Dto.Response.LoanResponseDTO;
import com.example.bookstore.Dto.Response.UserResponseDTO;
import com.example.bookstore.Model.User;
import com.example.bookstore.ServiceInterface.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    private UserResponseDTO convertToDto(User user){
        if(user == null) return null;
        List<LoanResponseDTO> loanDtos = null;
        if(user.getLoans() != null && !user.getLoans().isEmpty()){
            loanDtos = user.getLoans().stream()
                    .map(loan -> new LoanResponseDTO(
                            loan.getId(),
                            loan.getBorrowDate(),
                            loan.getReturnDate(),
                            (loan.getUser() != null) ? loan.getUser().getId() : null,
                            (loan.getUser() != null) ? loan.getUser().getFirstName() : null,
                            (loan.getBook() != null) ? loan.getBook().getId() : null,
                            (loan.getBook() != null) ? loan.getBook().getTitle() : null

                    ))
                    .toList();
        }
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                loanDtos
        );
    }
    private User convertToEntity(@Valid UserRequestDTO userRequestDTO){
        if(userRequestDTO == null) return null;
        return new User(
                null,
                userRequestDTO.getFirstName(),
                userRequestDTO.getLastName(),
                userRequestDTO.getAddress(),
                userRequestDTO.getUsername(),
                userRequestDTO.getPassword(),
                userRequestDTO.getEmail(),
                null
        );
    }
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
        User userToSave = convertToEntity(userRequestDTO);
        User savedUser = userService.saveUser(userToSave);
        return new ResponseEntity<>(convertToDto(savedUser), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        List<UserResponseDTO> userDTos = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(convertToDto(userOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        User userToUpdate = convertToEntity(userRequestDTO);
        User updatedUser = userService.updateUser(id, userToUpdate);
        if (updatedUser != null) {
            return ResponseEntity.ok(convertToDto(updatedUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
