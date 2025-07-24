package com.example.bookstore.Repository;

import com.example.bookstore.Model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    long countByUserIdAndReturnDateIsNull(Long userId);
}
