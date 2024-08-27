package com.Assignment.LibraryManagementSystem.dto;

import com.Assignment.LibraryManagementSystem.entity.Author;
import com.Assignment.LibraryManagementSystem.entity.Loan;
import com.Assignment.LibraryManagementSystem.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequest {
    private String title;
    private Author author;
    private long price;
}