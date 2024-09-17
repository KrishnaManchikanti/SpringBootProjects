package com.Assignment.LibraryManagementSystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AuthorDeletionException extends RuntimeException {
    public AuthorDeletionException(String s) {
        super(s);
    }
}