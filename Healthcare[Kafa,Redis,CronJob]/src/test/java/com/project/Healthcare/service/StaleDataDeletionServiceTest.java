package com.project.Healthcare.service;

import com.project.Healthcare.model.Consultation;
import com.project.Healthcare.repository.MedicalDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class StaleDataDeletionServiceTest {

    @InjectMocks
    private StaleDataDeletionService staleDataDeletionService;

    @Mock
    private MedicalDataRepository medicalDataRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteStaleData_Success() {
        // Set up test data
        LocalDateTime thresholdDate = LocalDateTime.now().minusDays(30);
        Consultation staleConsultation1 = new Consultation();
        staleConsultation1.setId(1L);
        staleConsultation1.setCreatedAt(thresholdDate.minusDays(1));

        Consultation staleConsultation2 = new Consultation();
        staleConsultation2.setId(2L);
        staleConsultation2.setCreatedAt(thresholdDate.minusDays(2));

        when(medicalDataRepository.findByCreatedAtBefore(thresholdDate))
                .thenReturn(Arrays.asList(staleConsultation1, staleConsultation2));


        staleDataDeletionService.deleteStaleData();


        verify(medicalDataRepository, times(1)).deleteAllById(Arrays.asList(1L, 2L));
    }

    @Test
    void testDeleteStaleData_NoStaleData() {
        LocalDateTime thresholdDate = LocalDateTime.now().minusDays(30);
        when(medicalDataRepository.findByCreatedAtBefore(thresholdDate))
                .thenReturn(Collections.emptyList());


        staleDataDeletionService.deleteStaleData();


        verify(medicalDataRepository, times(1)).deleteAllById(Collections.emptyList());
    }

    @Test
    void testDeleteStaleData_SingleStaleData() {
        LocalDateTime thresholdDate = LocalDateTime.now().minusDays(30);
        Consultation staleConsultation = new Consultation();
        staleConsultation.setId(1L);
        staleConsultation.setCreatedAt(thresholdDate.minusDays(1));

        when(medicalDataRepository.findByCreatedAtBefore(thresholdDate))
                .thenReturn(Collections.singletonList(staleConsultation));

        staleDataDeletionService.deleteStaleData();


        verify(medicalDataRepository, times(1)).deleteAllById(Collections.singletonList(1L));
    }
}

