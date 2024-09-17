package com.Assignment.LibraryManagementSystem.service;

import com.Assignment.LibraryManagementSystem.entity.Book;
import com.Assignment.LibraryManagementSystem.entity.Loan;
import com.Assignment.LibraryManagementSystem.entity.Status;
import com.Assignment.LibraryManagementSystem.entity.User;
import com.Assignment.LibraryManagementSystem.exceptions.*;
import com.Assignment.LibraryManagementSystem.repository.BookRepository;
import com.Assignment.LibraryManagementSystem.repository.LoanRepository;
import com.Assignment.LibraryManagementSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    /**
     * Finds a user by ID or throws an exception if not found.
     */
    private User findUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with ID {} not found", userId);
                    return new UserNotFoundException("User with ID " + userId + " not found.");
                });
    }

    /**
     * Finds a book by ID or throws an exception if not found.
     */
    private Book findBookById(long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    log.error("Book with ID {} not found", bookId);
                    return new BookNotFoundException("Book with ID " + bookId + " not found.");
                });
    }

    /**
     * Checks if a loan exists or throws an exception if not.
     */
    private void checkLoan(Loan loan, long bookId) {
        if (loan == null) {
            log.error("Loan for book with ID {} not found", bookId);
            throw new LoanNotFoundForBook("Loan not found for book with ID " + bookId);
        }
    }

    /**
     * Allows a user to borrow a book.
     */
    public void borrowBook(long userId, long bookId) {
        User user = findUserById(userId);
        Book book = findBookById(bookId);

        if (book.getUser() != null) {
            log.warn("Book with ID {} is already borrowed by another user", bookId);
            throw new BookAlreadyBorrowedException("Book with ID " + bookId + " is already borrowed.");
        }

        book.setUser(user);
        user.getBookList().add(book);

        Loan loan = book.getLoan();
        checkLoan(loan, bookId);

        loan.setStartDate(LocalDate.now());
        loan.setEndDate(LocalDate.now().plusWeeks(1));
        loan.setStatus(Status.BORROWED);
        loan.setBookId(bookId);
        loan.setUserId(userId);

        loanRepository.save(loan);
        userRepository.save(user);
        bookRepository.save(book);

        log.info("Borrowed book with ID {} to user with ID {}", bookId, userId);
    }

    /**
     * Allows a user to return a borrowed book.
     */
    public void returnBook(long userId, long bookId, double payingAmount) {
        if (payingAmount != amountToBePaid(userId, bookId)) {
            log.error("DueAmount {} and payingAmount {} are not same", amountToBePaid(userId, bookId), payingAmount);
            throw new PayableAmountMismatch("DueAmount and payingAmount are not same");
        }

        User user = findUserById(userId);
        Book book = findBookById(bookId);

        if (!book.getUser().equals(user)) {
            log.error("User with ID {} is not the current borrower of book with ID {}", userId, bookId);
            throw new UserMismatch("User with ID " + userId + " is not the current borrower of the book.");
        }

        book.setUser(null);
        if (!user.getBookList().remove(book)) {
            log.error("Book with ID {} not found in the book list of user with ID {}", bookId, userId);
            throw new BookNotFoundException("Book with ID {} not found in the book list of user with ID {}");
        }
        log.info("Book with ID {} is removed from book list of user with ID {}", bookId, userId);

        Loan loan = book.getLoan();
        checkLoan(loan, bookId);

        loan.setStatus(Status.RETURNED);
        loan.setEndDate(LocalDate.now());

        Loan newLoan = Loan.builder().status(Status.AVAILABLE).bookId(-1).userId(-1).build();
        newLoan.setBookId(bookId);
        book.setLoan(newLoan);

        loanRepository.save(loan);
        userRepository.save(user);
        bookRepository.save(book);

        log.info("Returned book with ID {} from user with ID {}", bookId, userId);
    }

    /**
     * Calculates the amount to be paid for a book return.
     */
    public double amountToBePaid(long userId, long bookId) {
        User user = findUserById(userId);
        Book book = findBookById(bookId);
        if (book.getUser() == null || !book.getUser().equals(user)) {
            log.info("Book is not borrowed or Book is associated with different user");
            throw new UserMismatch("Book is not borrowed or Book is associated with different user");
        }

        return getOverDueAmount(book);
    }

    /**
     * Calculates the overdue amount for a book.
     */
    private double getOverDueAmount(Book book) {
        long totalAmount = book.getPrice();
        double penaltyAmount = 0;
        long overDueDays = getNumOfOverDueDays(book.getLoan());
        if (overDueDays > 0) {
            double penaltyPerDay = totalAmount * (5 / 100.0); // 5% of TotalAmount
            log.info("penaltyPerDay is {}", penaltyPerDay);
            penaltyAmount = penaltyPerDay * overDueDays;
            book.getLoan().setStatus(Status.OVERDUE);
            bookRepository.save(book);
        }
        log.info("Total amount {} is inclusive of penaltyAmount {}", totalAmount + penaltyAmount, penaltyAmount);
        return totalAmount + penaltyAmount;
    }

    /**
     * Calculates the number of overdue days for a loan.
     */
    private long getNumOfOverDueDays(Loan loan) {
        return Period.between(loan.getEndDate(), LocalDate.now()).getDays();
    }

    /**
     * Retrieves all overdue loans and updates their status.
     */
    public List<Book> overdueLoans() {
        updateLoanStatus();
        log.info("Fetched all overdueLoans");
        return bookRepository.findAllByLoanStatus(Status.OVERDUE);
    }

    /**
     * Retrieves all loans and updates their status.
     */
    public List<Loan> getAllLoans() {
        updateLoanStatus();
        return loanRepository.findAll();
    }

    /**
     * Updates the status of loans for overdue books.
     */
    void updateLoanStatus() {
        List<Book> bookList = bookRepository.findAll();
        bookList = bookList.stream().filter(book -> book.getLoan() != null)
                .filter(book -> book.getLoan().getEndDate() != null)
                .toList();

        for (Book book : bookList) {
            long overDueDays = getNumOfOverDueDays(book.getLoan());
            if (overDueDays > 0) {
                book.getLoan().setStatus(Status.OVERDUE);
                bookRepository.save(book);
            }
        }
    }
}
