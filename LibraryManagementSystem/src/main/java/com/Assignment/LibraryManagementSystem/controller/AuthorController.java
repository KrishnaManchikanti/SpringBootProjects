package com.Assignment.LibraryManagementSystem.controller;

import com.Assignment.LibraryManagementSystem.dto.AuthorRequest;
import com.Assignment.LibraryManagementSystem.entity.Author;
import com.Assignment.LibraryManagementSystem.entity.Book;
import com.Assignment.LibraryManagementSystem.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
@Slf4j
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/register")
    public ResponseEntity<String> addAuthor(@Valid @RequestBody AuthorRequest authorRequest, BindingResult br) {
        if (br.hasErrors()) {
            log.warn("Validation failed for author request: {}", br.getAllErrors());
            throw new ValidationException("Validation failed for AuthorRequest");
        }
        log.info("Adding new author: {}", authorRequest);
        authorService.addAuthor(authorRequest);
        return ResponseEntity.status(201).body("Author created successfully");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Author>> getAllAuthors() {
        log.info("Fetching all authors");
        List<Author> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/all-books-by-author/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Book>> getAllBooksByAuthor(@PathVariable long id) {
        log.info("Fetching all books by author with ID: {}", id);
        List<Book> books = authorService.getAllBooksByAuthorId(id);
        return ResponseEntity.ok(books);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAuthor(@Valid @RequestBody AuthorRequest authorRequest, @PathVariable long id) {
        log.info("Updating author with ID: {} with data: {}", id, authorRequest);
        authorService.updateAuthor(authorRequest, id);
        return ResponseEntity.ok("Author updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable long id) {
        log.info("Deleting author with ID: {}", id);
        authorService.deleteAuthor(id);
        return ResponseEntity.ok("Author deleted successfully");
    }
}
