package com.project.Healthcare.service;

import com.project.Healthcare.model.MedicalData;
import com.project.Healthcare.repository.MedicalDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaleDataDeletionService {

    private final MedicalDataRepository medicalDataRepository;

    @Transactional
    public void deleteStaleData() {
        // Define the threshold date (e.g., data older than 30 days is considered stale)
        LocalDateTime thresholdDate = LocalDateTime.now().minusDays(30);
        List<Long> staleDataIds = medicalDataRepository.findByCreatedAtBefore(thresholdDate).stream()
                .map(MedicalData::getId).toList();

        // Delete stale data
        medicalDataRepository.deleteAllById(staleDataIds);

        // Log the number of records deleted
        log.info("Deleted {} stale records older than {}", staleDataIds.size(), thresholdDate);
    }
}