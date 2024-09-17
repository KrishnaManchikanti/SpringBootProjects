package com.Assignment.LibraryManagementSystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PayableAmountMismatch extends RuntimeException {
    public PayableAmountMismatch(String s) {
        super(s);
    }
}
