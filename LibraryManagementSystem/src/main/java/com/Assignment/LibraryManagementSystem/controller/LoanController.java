package com.Assignment.LibraryManagementSystem.controller;

import com.Assignment.LibraryManagementSystem.entity.Book;
import com.Assignment.LibraryManagementSystem.entity.Loan;
import com.Assignment.LibraryManagementSystem.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("loan")
@RequiredArgsConstructor
@Slf4j
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/borrow")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> borrowBook(@RequestParam long userId, @RequestParam long bookId) {
        log.info("User with ID: {} is borrowing book with ID: {}", userId, bookId);
        loanService.borrowBook(userId, bookId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Book borrowed successfully");
    }

    @PostMapping("/return")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> returnBook(@RequestParam long userId, @RequestParam long bookId, @RequestParam double amountToBePaid) {
        log.info("User with ID: {} is returning book with ID: {} by payingAmount: {}", userId, bookId, amountToBePaid);
        loanService.returnBook(userId, bookId, amountToBePaid);
        return ResponseEntity.ok("Book returned successfully");
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Book>> getOverdueLoans() {
        log.info("Fetching all overdue loans");
        List<Book> overdueBooks = loanService.overdueLoans();
        return ResponseEntity.ok(overdueBooks);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Loan>> getAllLoans() {
        log.info("Fetching all loans");
        List<Loan> allLoans = loanService.getAllLoans();
        return ResponseEntity.ok(allLoans);
    }

    @GetMapping("/amount-to-be-paid")
    public ResponseEntity<Double> getAmountToBePaid(@RequestParam long userId, @RequestParam long bookId) {
        log.info("Fetching the amount to be paid for userId: {} and bookId: {}", userId, bookId);
        double amountToBePaid = loanService.amountToBePaid(userId, bookId);
        return ResponseEntity.ok(amountToBePaid);
    }
}