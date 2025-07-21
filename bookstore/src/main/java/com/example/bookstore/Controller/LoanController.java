package com.example.bookstore.Controller;

import com.example.bookstore.Dto.Request.LoanRequestDTO;
import com.example.bookstore.Dto.Response.LoanResponseDTO;
import com.example.bookstore.Model.Loan;
import com.example.bookstore.Service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController (LoanService loanService){
        this.loanService = loanService;
    }
    private LoanResponseDTO convertToDto(Loan loan) {
        if (loan == null) return null;
        return new LoanResponseDTO(
                loan.getId(),
                loan.getBorrowDate(),
                loan.getReturnDate(),
                loan.getUser() != null ? loan.getUser().getId() : null,
                loan.getUser() != null ? loan.getUser().getFirstName() : null,
                loan.getBook() != null ? loan.getBook().getId() : null,
                loan.getBook() != null ? loan.getBook().getTitle() : null
        );
    }
    @PostMapping("/borrow")
    public ResponseEntity<LoanResponseDTO> borrowBook(@Valid @RequestBody LoanRequestDTO loanRequestDTO) {
        Optional<Loan> loanOptional = loanService.borrowBook(
                loanRequestDTO.getUserId(),
                loanRequestDTO.getBookId()
        );
        return loanOptional.
                map(loan -> new ResponseEntity<>(convertToDto(loan), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    @PutMapping("/return/{loanId}")
    public ResponseEntity<LoanResponseDTO> returnBook(@PathVariable Long loanId) {
        Optional<Loan> loanOptional = loanService.returnBook(loanId);
        return loanOptional
                .map(loan -> ResponseEntity.ok(convertToDto(loan)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
