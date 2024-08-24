package com.Assignment.LibraryManagementSystem.repository;

import com.Assignment.LibraryManagementSystem.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
