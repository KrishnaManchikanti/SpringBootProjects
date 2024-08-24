package com.Assignment.LibraryManagementSystem.controller;

import com.Assignment.LibraryManagementSystem.dto.BookRequest;
import com.Assignment.LibraryManagementSystem.entity.Book;
import com.Assignment.LibraryManagementSystem.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('AUTHOR')")
    public void addBook(@Valid @RequestBody BookRequest bookRequest, BindingResult br) {
        if (br.hasErrors()) {
            log.warn("Validation failed for book request: {}", br.getAllErrors());
            throw new ValidationException("Validation failed for BookRequest");
        }
        log.info("Saving book: {}", bookRequest);
        bookService.addBook(bookRequest);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooks() {
        log.info("Fetching all books");
        return bookService.getAllBooks();
    }

    @GetMapping("/all-available")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllAvailableBooks() {
        log.info("Fetching all available books");
        return bookService.getAllAvailableBooks();
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('AUTHOR')")
    public void updateBook(@RequestBody BookRequest bookRequest, @PathVariable long id) {
        log.info("Updating book with ID: {} with data: {}", id, bookRequest);
        bookService.updateBook(bookRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('AUTHOR')")
    public void deleteBook(@PathVariable long id) {
        log.info("Deleting book with ID: {}", id);
        bookService.deleteBook(id);
    }

}
