package com.project.Healthcare.exception;

public class ErrorReadingJsonException extends RuntimeException {
    public ErrorReadingJsonException(String failedToParseJson) {
        super(failedToParseJson);
    }
}
