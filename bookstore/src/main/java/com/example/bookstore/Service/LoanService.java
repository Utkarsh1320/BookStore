package com.example.bookstore.Service;


import com.example.bookstore.Model.Loan;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface LoanService {
    Optional<Loan> borrowBook(Long userId, Long bookId);
    Optional<Loan> returnBook(Long loanId);
}
