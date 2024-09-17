package com.Assignment.LibraryManagementSystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookDeletionException extends RuntimeException {
    public BookDeletionException(String s) {
        super(s);
    }
}