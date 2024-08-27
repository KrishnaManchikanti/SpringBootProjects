package com.Assignment.LibraryManagementSystem.service;

import com.Assignment.LibraryManagementSystem.dto.BookRequest;
import com.Assignment.LibraryManagementSystem.entity.Author;
import com.Assignment.LibraryManagementSystem.entity.Book;
import com.Assignment.LibraryManagementSystem.entity.Loan;
import com.Assignment.LibraryManagementSystem.entity.Status;
import com.Assignment.LibraryManagementSystem.exceptions.BookDeletionException;
import com.Assignment.LibraryManagementSystem.exceptions.BookNotFoundException;
import com.Assignment.LibraryManagementSystem.repository.AuthorRepository;
import com.Assignment.LibraryManagementSystem.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final LoanService loanService;

    /**
     * Adds a new book, creating or updating the associated author.
     */
    public void addBook(BookRequest book) {

        long authorId = book.getAuthor().getId();
        Author author;

        if (authorRepository.existsById(authorId)) {
            author = authorRepository.findById(authorId).get();
        } else {
            author = book.getAuthor();
            author = authorRepository.save(author);
        }

        Book newBook = Book.builder()
                .user(null)
                .loan(new Loan(Status.AVAILABLE))
                .price(book.getPrice())
                .title(book.getTitle())
                .author(author)
                .build();

        bookRepository.save(newBook);
        log.info("Added new book with title: {}", newBook.getTitle());
    }

    /**
     * Retrieves a list of all books.
     */
    public List<Book> getAllBooks() {
        log.info("Fetched all books");
        return bookRepository.findAll();
    }

    /**
     * Retrieves a list of all available books.
     */
    public List<Book> getAllBooksByStatus(String loanStatus) {
        log.info("Fetching Books Started");
        loanService.updateLoanStatus();
        if (loanStatus.equalsIgnoreCase("borrowed")){
            log.info("Fetched all {}: books",loanStatus);
            return bookRepository.findAllByLoanDifferentStatus();
        }
        log.info("Fetched all {}: books",loanStatus);
        return bookRepository.findAllByLoanStatus(Status.valueOf(loanStatus.toUpperCase()));
    }

    /**
     * Deletes a book by ID if it is not borrowed or overdue.
     */
    public void deleteBook(long id) {
        Book existingBook = getBookById(id);

        if (existingBook.getLoan().getStatus().equals(Status.BORROWED) || existingBook.getLoan().getStatus().equals(Status.OVERDUE)) {
            log.warn("Cannot delete book with ID {} because it is currently Borrowed/Overdue", id);
            throw new BookDeletionException("Cannot delete book because it is currently Borrowed/Overdue");
        }

        Author author = existingBook.getAuthor();
        if (!author.getBookList().remove(existingBook)) {
            log.warn("Cannot remove existingBook with ID {} from author with id {}", id, author.getId());
            throw new BookDeletionException("Cannot delete book because it is not present with author");
        }

        bookRepository.deleteById(id);
        log.info("Deleted book with ID: {}", id);

        if (author.getBookList().isEmpty()) {
            log.info("No remaining books for author with ID {}; deleting author", author.getId());
            authorRepository.delete(author);
            log.info("Deleted author with ID: {}", author.getId());
        }
    }

    /**
     * Retrieves a book by ID or throws an exception if not found.
     */
    private Book getBookById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Book with ID {} not found", id);
                    return new BookNotFoundException("Book with ID " + id + " not found.");
                });
    }

    /**
     * Updates an existing book with new details.
     */
    public void updateBook(BookRequest bookRequest, long id) {

        Book existingBook = getBookById(id);

        if (bookRequest.getTitle() != null)
            existingBook.setTitle(bookRequest.getTitle());

        if (bookRequest.getAuthor() != null)
            existingBook.setAuthor(bookRequest.getAuthor());

        bookRepository.save(existingBook);//try-catch
        log.info("Updated book with ID: {} with new data", id);
    }

    public List<Book> getOrderHistory() {
        return bookRepository.GetOrderHistory();
    }
}
