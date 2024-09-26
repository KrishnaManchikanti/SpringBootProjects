package com.project.Healthcare.service;

import com.project.Healthcare.model.Patient;
import com.project.Healthcare.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KafkaEventsConsumerTest {

    @InjectMocks
    private KafkaEventsConsumer kafkaEventsConsumer;

    @Mock
    private PatientRepository patientRepository;

    private Patient testPatient;

    @BeforeEach
    void setUp() {
        testPatient = new Patient(1L, "John Doe", 30, "123 Main St", "1234567890", null, null);
    }

    @Test
    void testConsumePatientDataSuccess() {
        // Call the method to be tested
        kafkaEventsConsumer.consumePatientData(testPatient);

        // Verify that the patientRepository's save method was called
        verify(patientRepository, times(1)).save(testPatient);
    }

    @Test
    void testConsumePatientDataFailure() {
        // When saving the patient, throw an exception
        doThrow(new RuntimeException("Database error")).when(patientRepository).save(testPatient);

        // Call the method to be tested
        kafkaEventsConsumer.consumePatientData(testPatient);

        // Verify that the patientRepository's save method was called
        verify(patientRepository, times(1)).save(testPatient);
        // Optionally: Check if the error log was called (if you have a Logger mocked)
        // In this case, logging cannot be verified directly without additional libraries or a specific setup
    }
}

