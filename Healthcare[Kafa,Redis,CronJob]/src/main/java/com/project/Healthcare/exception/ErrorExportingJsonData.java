package com.project.Healthcare.exception;

public class ErrorExportingJsonData extends RuntimeException {
    public ErrorExportingJsonData(String message) {
        super(message);
    }
}
