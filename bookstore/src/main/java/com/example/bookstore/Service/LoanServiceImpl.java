package com.example.bookstore.Service;

import com.example.bookstore.Exceptions.LoanLimitExceededException;
import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.Loan;
import com.example.bookstore.Model.User;
import com.example.bookstore.Repository.BookRepository;
import com.example.bookstore.Repository.LoanRepository;
import com.example.bookstore.Repository.UserRepository;
import com.example.bookstore.ServiceInterface.LoanService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    private static final int MAX_ACTIVE_LOANS = 5;

    public LoanServiceImpl(
            LoanRepository loanRepository,
            BookRepository bookRepository,
            UserRepository userRepository){
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }
    @Override
    @Transactional
    public Optional<Loan> borrowBook(Long userId, Long bookId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if(userOptional.isPresent() && bookOptional.isPresent()){
            User user = userOptional.get();
            Book book = bookOptional.get();

            if(book.getAvailableCopies() <= 0){
                throw new IllegalArgumentException("Book '" +
                        book.getTitle() + "' has no available copies for borrowing");
            }
            long activeLoanCount = loanRepository.countByUserIdAndReturnDateIsNull(user.getId());
            if(activeLoanCount >= MAX_ACTIVE_LOANS){
                throw new LoanLimitExceededException(
                        "User '" + user.getFirstName() + "' has reached the maximum limit of "+
                         MAX_ACTIVE_LOANS + " active loans."
                );
            }
                Loan loan = new Loan();
                loan.setBorrowDate(LocalDate.now());
                loan.setUser(user);
                loan.setBook(book);

                book.setAvailableCopies(book.getAvailableCopies() - 1);
                bookRepository.save(book);

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
                throw new IllegalArgumentException("Book '"+  loanId +
                        "has already been returned");
            }
            loan.setReturnDate(LocalDate.now());
            Book book = loan.getBook();
            if(book.getAvailableCopies() < book.getTotalCopies()){
                book.setAvailableCopies(book.getAvailableCopies() + 1);
            }
            bookRepository.save(book);

            Loan updatedLoan = loanRepository.save(loan);
            return Optional.of(updatedLoan);
        }
        return Optional.empty();
    }
}
