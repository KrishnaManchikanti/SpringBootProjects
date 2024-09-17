package com.Assignment.LibraryManagementSystem.dto;

import com.Assignment.LibraryManagementSystem.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorRequest {
    private String name;
}