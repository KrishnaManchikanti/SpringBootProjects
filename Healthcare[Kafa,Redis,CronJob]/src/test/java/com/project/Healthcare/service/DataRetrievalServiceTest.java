package com.project.Healthcare.service;

import com.project.Healthcare.exception.PatientNotFound;
import com.project.Healthcare.model.Patient;
import com.project.Healthcare.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataRetrievalServiceTest {

    @InjectMocks
    private DataRetrievalService dataRetrievalService;

    @Mock
    private PatientRepository patientRepository;

    private Patient patient1;
    private Patient patient2;

    @BeforeEach
    public void setUp() {
        patient1 = new Patient(1L, "John Doe", 30, "123 Main St", "1234567890", null, null);
        patient2 = new Patient(2L, "Jane Smith", 28, "456 Elm St", "0987654321", null, null);
    }

    @Test
    public void testGetAllPatient() {
        // Mocking Pageable and Specification
        Pageable pageable = mock(Pageable.class);
        Specification<Patient> spec = mock(Specification.class);

        // Mocking the repository behavior
        Page<Patient> mockPage = new PageImpl<>(Arrays.asList(patient1, patient2));
        when(patientRepository.findAll(spec, pageable)).thenReturn(mockPage);

        // Call the method
        Page<Patient> result = dataRetrievalService.getAllPatient(pageable, spec);

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(patientRepository, times(1)).findAll(spec, pageable);
    }

    @Test
    public void testFindByIdSuccess() {
        // Mocking the behavior of the repository
        System.out.println(patient1);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient1));

        // Call the method
        Patient result = dataRetrievalService.findById(1L);

        // Assertions
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdNotFound() {
        // Mocking the behavior of the repository to return empty
        when(patientRepository.findById(999L)).thenReturn(Optional.empty());

        // Verify exception is thrown
        PatientNotFound thrown = assertThrows(PatientNotFound.class, () -> {
            dataRetrievalService.findById(999L);
        });

        assertEquals("Patient not found", thrown.getMessage());
        verify(patientRepository, times(1)).findById(999L);
    }
}
