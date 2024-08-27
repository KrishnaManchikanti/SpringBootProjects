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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    public void testAddAuthor_Success() throws Exception {
        AuthorRequest authorRequest = new AuthorRequest("John");
        mockMvc.perform(post("/author/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorRequest)))
                .andExpect(status().isCreated());

        verify(authorService).addAuthor(authorRequest);
    }

    @Test
    @WithMockUser("AUTHOR")
    public void testGetListOfAuthors_ReturnsAuthors() throws Exception {
        Author author = new Author();
        author.setName("name");
        List<Author> authors = List.of(author);
        when(authorService.getAllAuthors()).thenReturn(authors);

        mockMvc.perform(get("/author/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(authors)));
    }

    @Test
    @WithMockUser("AUTHOR")
    public void testGetBooksByAuthor_ReturnsBooks() throws Exception {
        Book book = new Book();
        book.setTitle("Book Title");
        when(authorService.getAllBooksByAuthorId(1L)).thenReturn(List.of(book));

        mockMvc.perform(get("/author/all-books-by-author/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(book))));
    }

    @Test
    @WithMockUser("AUTHOR")
    public void testUpdateAuthor_Success() throws Exception {
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setName("Updated Name");

        mockMvc.perform(put("/author/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authorRequest)))
                .andExpect(status().isOk());

        verify(authorService).updateAuthor(authorRequest, 1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteAuthor_Success() throws Exception {
        long authorId = 1L;

        doNothing().when(authorService).deleteAuthor(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/author/delete/{id}", authorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(authorService).deleteAuthor(authorId);
    }

}