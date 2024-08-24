package com.Assignment.LibraryManagementSystem.controller;

import com.Assignment.LibraryManagementSystem.dto.AuthorRequest;
import com.Assignment.LibraryManagementSystem.entity.Author;
import com.Assignment.LibraryManagementSystem.entity.Book;
import com.Assignment.LibraryManagementSystem.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = AuthorController.class)
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "AUTHOR")
    public void shouldTestAddAuthorSuccess() throws Exception {
        AuthorRequest authorRequest = new AuthorRequest("John", new ArrayList<>());
        mockMvc.perform(post("/author/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorRequest)))
                .andExpect(status().isCreated());

        verify(authorService).addAuthor(authorRequest);
    }

    @Test
    @WithMockUser("AUTHOR")
    public void shouldTestGetAllAuthors() throws Exception {

        Author author = new Author();
        author.setName("name");
        when(authorService.getAllAuthors())
                .thenReturn(List.of(author));

        mockMvc.perform(get("/author/all"))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser("AUTHOR")
    public void shouldTestGetAllBooksByAuthor() throws Exception {

        Book book = new Book();
        book.setTitle("Book Title");
        when(authorService.getAllBooksByAuthorId(1L))
                .thenReturn(List.of(book));

        mockMvc.perform(get("/author/all-books-by-author/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("AUTHOR")
    public void shouldTestUpdateAuthor() throws Exception {

        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setName("Updated Name");

        mockMvc.perform(put("/author/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authorRequest)))
                .andExpect(status().isOk());

        verify(authorService).updateAuthor(authorRequest, 1L);
    }

    @Test
    @WithMockUser(roles = "AUTHOR")
    public void shouldTestDeleteAuthor() throws Exception {


        doNothing().when(authorService).deleteAuthor(anyLong());

        mockMvc.perform(delete("/author/delete/{id}", 1L))
                .andExpect(status().isOk());

        verify(authorService).deleteAuthor(1L);

    }

}

