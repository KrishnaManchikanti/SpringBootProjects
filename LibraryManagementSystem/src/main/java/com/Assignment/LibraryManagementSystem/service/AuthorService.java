package com.Assignment.LibraryManagementSystem.service;

import com.Assignment.LibraryManagementSystem.dto.AuthorRequest;
import com.Assignment.LibraryManagementSystem.entity.Author;
import com.Assignment.LibraryManagementSystem.entity.Book;
import com.Assignment.LibraryManagementSystem.exceptions.AuthorDeletionException;
import com.Assignment.LibraryManagementSystem.exceptions.AuthorNotFoundException;
import com.Assignment.LibraryManagementSystem.exceptions.InvalidInputException;
import com.Assignment.LibraryManagementSystem.repository.AuthorRepository;
import com.Assignment.LibraryManagementSystem.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    /**
     * Adds a new author to the repository.
     */
    public void addAuthor(AuthorRequest authorRequest) {
        Author author = new Author();
        author.setName(authorRequest.getName());

        authorRepository.save(author);
        log.info("Author added successfully: {}", author);
    }

    /**
     * Retrieves all authors from the repository.
     */
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    /**
     * Deletes an author by ID if no associated books exist.
     */
    public void deleteAuthor(long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author with ID " + id + " not found"));

        if (!author.getBookList().isEmpty()) {
            throw new AuthorDeletionException("Author with ID " + id + " cannot be deleted as associated books are available");
        }

        authorRepository.deleteById(id);
        log.info("Author deleted successfully with ID: {}", id);
    }

    /**
     * Updates an existing author by ID with new details.
     */
    public void updateAuthor(AuthorRequest authorRequest, long id)  {

        log.info("Received request to update author with ID: {}", id);

        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isEmpty()) {
            log.error("Author with ID {} not found", id);
            throw new AuthorNotFoundException("Author with ID " + id + " not found");
        }

       Author  author = optionalAuthor.get();
        if (authorRequest.getName() != null) {
            author.setName(authorRequest.getName());
            authorRepository.save(author);
            log.info("Author updated successfully with ID: {}", id);
        } else {
            log.error("Author with ID not updated as Name is empty: {}", id);
            throw new InvalidInputException("Name cannot be empty");
        }
    }

    /**
     * Retrieves all books written by a specific author ID.
     */
    public List<Book> getAllBooksByAuthorId(long authorId) {

        log.info("Fetched all books written by Author with ID: {}", authorId);
        return bookRepository.getAllBooksByAuthorId(authorId);
    }
}
