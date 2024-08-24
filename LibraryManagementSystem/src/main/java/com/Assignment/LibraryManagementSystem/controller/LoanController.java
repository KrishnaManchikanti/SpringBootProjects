package com.Assignment.LibraryManagementSystem.controller;

import com.Assignment.LibraryManagementSystem.entity.Book;
import com.Assignment.LibraryManagementSystem.entity.Loan;
import com.Assignment.LibraryManagementSystem.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    public void borrowBook(@RequestParam long userId, @RequestParam long bookId) {
        log.info("User with ID: {} is borrowing book with ID: {}", userId, bookId);
        loanService.borrowBook(userId, bookId);
    }

    @PostMapping("/return")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public void returnBook(@RequestParam long userId, @RequestParam long bookId, @RequestParam double amountToBePaid) {
        log.info("User with ID: {} is returning book with ID: {} by payingAmount: {}", userId, bookId, amountToBePaid);
        loanService.returnBook(userId, bookId, amountToBePaid);
    }

    @GetMapping("/all-borrowed")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBorrowedBooks() {
        log.info("Fetching all borrowed books");
        return loanService.getAllBorrowBooks();
    }

    @GetMapping("/overdue")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getOverdueLoans() {
        log.info("Fetching all overdue loans");
        return loanService.overdueLoans();
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Loan> getAllLoans() {
        log.info("Fetching all loans");
        return loanService.getAllLoans();
    }

    @GetMapping("/amount-to-be-paid")
    @ResponseStatus(HttpStatus.OK)
    public double getAmountToBePaid(@RequestParam long userId, @RequestParam long bookId) {
        log.info("Fetching the amount to be Paid");
        return loanService.amountToBePaid(userId, bookId);
    }
}