package com.Assignment.LibraryManagementSystem.controller;

import com.Assignment.LibraryManagementSystem.dto.AuthorRequest;
import com.Assignment.LibraryManagementSystem.entity.Author;
import com.Assignment.LibraryManagementSystem.entity.Book;
import com.Assignment.LibraryManagementSystem.service.AuthorService;
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
@RequestMapping("/author")
@RequiredArgsConstructor
@Slf4j
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAuthor(@Valid @RequestBody AuthorRequest authorRequest, BindingResult br){
        if(br.hasErrors()){
            log.warn("Validation failed for author request: {}", br.getAllErrors());
            throw new ValidationException(("Validation failed for AuthorRequest"));
        }
        log.info("Adding new author: {}", authorRequest);
        authorService.addAuthor(authorRequest);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<Author> getAllAuthors(){
        log.info("Fetching all authors");
        return authorService.getAllAuthors();
    }

    @GetMapping("/all-books-by-author/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<Book> getAllBooksByAuthor(@PathVariable long id){
        log.info("Fetching all books by author");
        return authorService.getAllBooksByAuthorId(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateAuthor(@RequestBody AuthorRequest authorRequest, @PathVariable long id){
        log.info("Updating author with ID: {} with data: {}", id, authorRequest);
        authorService.updateAuthor(authorRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAuthor(@PathVariable long id){
        log.info("Deleting author with ID: {}", id);
        authorService.deleteAuthor(id);
    }
}
