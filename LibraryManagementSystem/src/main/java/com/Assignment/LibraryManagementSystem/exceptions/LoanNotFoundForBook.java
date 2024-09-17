package com.Assignment.LibraryManagementSystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LoanNotFoundForBook extends RuntimeException {
    public LoanNotFoundForBook(String s) {
        super(s);
    }
}