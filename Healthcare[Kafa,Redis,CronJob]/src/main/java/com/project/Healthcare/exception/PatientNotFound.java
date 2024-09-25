package com.project.Healthcare.exception;

public class PatientNotFound extends RuntimeException {
    public PatientNotFound(String patientNotFound) {
        super(patientNotFound);
    }
}
