package com.Assignment.LibraryManagementSystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>("ValidationException: ConstraintViolationImpl 'size must be valid", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<String> handleAuthorException(AuthorNotFoundException ex) {
        return new ResponseEntity<>("Author Not Found", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorDeletionException.class)
    public ResponseEntity<String> handleAuthorException(AuthorDeletionException ex) {
        return new ResponseEntity<>("Author cannot be deleted as associated books are available", HttpStatus.BAD_REQUEST);
    }
}