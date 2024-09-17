package com.Assignment.LibraryManagementSystem.controller;

import com.Assignment.LibraryManagementSystem.dto.BookRequest;
import com.Assignment.LibraryManagementSystem.entity.Book;
import com.Assignment.LibraryManagementSystem.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('AUTHOR')")
    public ResponseEntity<String> addBook(@Valid @RequestBody BookRequest bookRequest, BindingResult br) {
        if (br.hasErrors()) {
            log.warn("Validation failed for book request: {}", br.getAllErrors());
            throw new ValidationException("Validation failed for BookRequest");
        }
        log.info("Saving book: {}", bookRequest);
        bookService.addBook(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Book added successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        log.info("Fetching all books");
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/all/{loanStatus}")
    public ResponseEntity<List<Book>> getAllBooksByStatus(@PathVariable String loanStatus) {
        log.info("Fetching all books by loanStatus: {}", loanStatus);
        List<Book> books = bookService.getAllBooksByStatus(loanStatus);
        return ResponseEntity.ok(books);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('AUTHOR')")
    public ResponseEntity<String> updateBook(@RequestBody BookRequest bookRequest, @PathVariable long id) {
        log.info("Updating book with ID: {} with data: {}", id, bookRequest);
        bookService.updateBook(bookRequest, id);
        return ResponseEntity.ok("Book updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('AUTHOR')")
    public ResponseEntity<String> deleteBook(@PathVariable long id) {
        log.info("Deleting book with ID: {}", id);
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }

    @GetMapping("/orderHistory")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Book>> getOrderHistory(){
        return ResponseEntity.ok(bookService.getOrderHistory());
    }
}
