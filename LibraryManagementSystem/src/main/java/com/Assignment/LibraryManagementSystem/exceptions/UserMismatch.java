package com.Assignment.LibraryManagementSystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserMismatch extends RuntimeException {
    public UserMismatch(String s) {
        super(s);
    }
}