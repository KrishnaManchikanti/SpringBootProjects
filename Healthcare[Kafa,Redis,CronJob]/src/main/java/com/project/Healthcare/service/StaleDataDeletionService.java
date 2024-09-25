package com.project.Healthcare.service;

import com.project.Healthcare.model.Consultation;
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
        log.info("Identifying stale records older than {}", thresholdDate);

        // Find stale data
        List<Consultation> staleData = medicalDataRepository.findByCreatedAtBefore(thresholdDate);
        List<Long> staleDataIds = staleData.stream()
                .map(Consultation::getId)
                .toList();

        // If there are stale records, delete them
        if (!staleDataIds.isEmpty()) {
            medicalDataRepository.deleteAllById(staleDataIds);
            log.info("Deleted {} stale records older than {}", staleDataIds.size(), thresholdDate);
        } else {
            log.info("No stale records found older than {}", thresholdDate);
        }
    }
}
