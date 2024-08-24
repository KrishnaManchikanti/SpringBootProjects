package com.Assignment.LibraryManagementSystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.Assignment.LibraryManagementSystem.dto.AuthorRequest;
import com.Assignment.LibraryManagementSystem.entity.Author;
import com.Assignment.LibraryManagementSystem.entity.Book;
import com.Assignment.LibraryManagementSystem.exceptions.AuthorDeletionException;
import com.Assignment.LibraryManagementSystem.exceptions.AuthorNotFoundException;
import com.Assignment.LibraryManagementSystem.repository.AuthorRepository;
import com.Assignment.LibraryManagementSystem.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AuthorServiceTest {

    private static final Logger log = LoggerFactory.getLogger(AuthorServiceTest.class);
    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("TestAuthorAdd")
    public void shouldTestAddAuthor() {

        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setName("John Doe");
        authorRequest.setBookList(Arrays.asList(new Book(), new Book()));

        Mockito.when(authorRepository.save(Mockito.any(Author.class)))
                .thenReturn(new Author(authorRequest.getBookList()));

        authorService.addAuthor(authorRequest);
        verify(authorRepository).save(any(Author.class));
    }

    @Test
    @DisplayName("TestGetAllAuthors")
    public void shouldTestAllAuthors(){

        List<Author> expectedAuthorList = new ArrayList<>();
        expectedAuthorList.add(new Author());

        when(authorRepository.findAll()).thenReturn(expectedAuthorList);
        List<Author> authorList = authorRepository.findAll();
        Assertions.assertEquals(expectedAuthorList,authorList);

    }

    @Test
    @DisplayName("TestDeleteAuthor")
    public void shouldTestDeleteAuthorSuccess(){

        long id = 1;
        Author expectdAuthor = new Author();
        expectdAuthor.setName("krishna");

        expectdAuthor.setBookList(new ArrayList<>());

        when(authorRepository.findById(id)).thenReturn(Optional.of(expectdAuthor));

        authorService.deleteAuthor(1);

        verify(authorRepository).deleteById(any(Long.class));
    }

    @Test
    @DisplayName("TestDeleteAuthorFailureAssociateBooks")
    public void shouldTestDeleteAuthorFailure(){

        long id = 1;
        Author expectdAuthor = new Author();
        expectdAuthor.setName("krishna");
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book());
        expectdAuthor.setBookList(bookList);

        when(authorRepository.findById(id)).thenReturn(Optional.of(expectdAuthor));

        AuthorDeletionException exception = assertThrows(AuthorDeletionException.class, () -> {
            authorService.deleteAuthor(id);
        });

        assertEquals("Author with ID " + id + " cannot be deleted as associated books are available", exception.getMessage());
//      assertEquals(AuthorDeletionException.class, exception.getClass());
        verify(authorRepository, never()).deleteById(id);
    }

    @Test
    @DisplayName("FailureAuthorIdNotFound")
    public void shouldTestDeleteAuthorFailureById(){

        long id = 1;
        Author expectdAuthor = new Author();
        expectdAuthor.setName("krishna");
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book());
        expectdAuthor.setBookList(bookList);

        when(authorRepository.findById(id)).thenReturn(Optional.of(expectdAuthor));

        AuthorNotFoundException exception = assertThrows(AuthorNotFoundException.class, () -> {
            authorService.deleteAuthor(5);
        });

      assertEquals(AuthorNotFoundException.class, exception.getClass());
        verify(authorRepository, never()).deleteById(id);
    }


    @Test
    @DisplayName("UpdateAuthor")
    public void testUpdateAuthor(){
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setName("John Doe");
        authorRequest.setBookList(Arrays.asList(new Book(), new Book()));

        long id =1;
        Author expectedAuthor = new Author(authorRequest.getBookList());
        when(authorRepository.findById(id)).thenReturn(Optional.of(expectedAuthor));

        authorRepository.save(expectedAuthor);

        verify(authorRepository).save(any(Author.class));
    }

    @Test
    @DisplayName("UpdateAuthorFailure")
    public void testUpdateAuthorFailure(){
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setBookList(Arrays.asList(new Book(), new Book()));
        System.out.println(authorRequest.getName());
        long id =1;

        Author expectedAuthor = new Author(authorRequest.getBookList());
        when(authorRepository.findById(id)).thenReturn(Optional.of(expectedAuthor));

        authorService.updateAuthor(authorRequest, id);

        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    @DisplayName("ListOfBooksByAuthorId")
    public void shouldTestGetAllBooksByAuthorId() {

        long authorId =1;
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book());

        when(bookRepository.getAllBooksByAuthorId(authorId)).thenReturn(bookList);
        List<Book> output = bookRepository.getAllBooksByAuthorId(authorId);
        Assertions.assertEquals(bookList, output);
    }


}
