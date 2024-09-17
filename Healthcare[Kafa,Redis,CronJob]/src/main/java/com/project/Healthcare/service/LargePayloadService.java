package com.project.Healthcare.service;

import com.project.Healthcare.model.MedicalData;
import com.project.Healthcare.repository.MedicalDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LargePayloadService {

    private final MedicalDataRepository medicalDataRepository;

    @Async
    public void processLargePayload(MedicalData entity) {
        // Process the large payload asynchronously
        medicalDataRepository.save(entity);
        log.info("Large payload processed and saved");
    }
}

