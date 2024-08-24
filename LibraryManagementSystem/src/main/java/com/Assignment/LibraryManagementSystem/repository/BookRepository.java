package com.Assignment.LibraryManagementSystem.repository;


import com.Assignment.LibraryManagementSystem.entity.Book;
import com.Assignment.LibraryManagementSystem.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByLoanStatus(Status status);

    @Query("SELECT b FROM Book b JOIN b.loan l WHERE l.status = 'OVERDUE' OR l.status = 'BORROWED'")
    List<Book> findAllByLoanDifferentStatus();

    List<Book> getAllBooksByAuthorId(long id);
}
