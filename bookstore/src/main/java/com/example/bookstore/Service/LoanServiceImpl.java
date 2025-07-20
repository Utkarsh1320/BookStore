package com.example.bookstore.Service;

import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.Loan;
import com.example.bookstore.Model.User;
import com.example.bookstore.Repository.BookRepository;
import com.example.bookstore.Repository.LoanRepository;
import com.example.bookstore.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class LoanServiceImpl implements LoanService{
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanServiceImpl(
            LoanRepository loanRepository,
            BookRepository bookRepository,
            UserRepository userRepository){
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }
    @Override
    public Optional<Loan> borrowBook(Long userId, Long bookId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if(userOptional.isPresent() && bookOptional.isPresent()){
            User user = userOptional.get();
            Book book = bookOptional.get();

            boolean isBookAlreadyBorrowed = loanRepository.findAll().stream()
                    .filter(loan -> loan.getBook().equals(book))
                    .anyMatch(loan -> loan.getReturnDate() == null);

            if(isBookAlreadyBorrowed){
                return Optional.empty();
            }
                Loan loan = new Loan();
                loan.setBorrowDate(LocalDate.now());
                loan.setUser(user);
                loan.setBook(book);

                Loan savedLoan = loanRepository.save(loan);
                return Optional.of(savedLoan);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Loan> returnBook(Long loanId) {
        Optional<Loan> loanOptional = loanRepository.findById(loanId);
        if(loanOptional.isPresent()){
            Loan loan = loanOptional.get();
            if(loan.getReturnDate() != null){
                return Optional.empty();
            }
            loan.setReturnDate(LocalDate.now());

            Loan updatedLoan = loanRepository.save(loan);
            return Optional.of(updatedLoan);
        }
        return Optional.empty();
    }
}
