package com.example.bookstore.ServiceInterface;


import com.example.bookstore.Model.Loan;

import java.util.Optional;

public interface LoanService {
    Optional<Loan> borrowBook(Long userId, Long bookId);
    Optional<Loan> returnBook(Long loanId);
}
