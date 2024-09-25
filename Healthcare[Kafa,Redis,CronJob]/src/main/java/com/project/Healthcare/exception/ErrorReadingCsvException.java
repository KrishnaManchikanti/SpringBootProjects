package com.project.Healthcare.exception;

public class ErrorReadingCsvException extends RuntimeException {
    public ErrorReadingCsvException(String message){
        super(message);
    }
}
