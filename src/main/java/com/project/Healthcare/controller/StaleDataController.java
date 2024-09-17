package com.project.Healthcare.controller;

import com.project.Healthcare.service.StaleDataDeletionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stale-data")
@RequiredArgsConstructor
@Slf4j
public class StaleDataController {

    private final StaleDataDeletionService staleDataDeletionService;

    // Endpoint to trigger the deletion of stale data manually
    @PostMapping("/delete")
    public String deleteStaleDataManually() {
        log.info("Manual trigger to delete stale data initiated...");
        try {
            staleDataDeletionService.deleteStaleData();
            return "Stale data deletion executed successfully!";
        } catch (Exception e) {
            log.error("Error during manual stale data deletion", e);
            return "Error during stale data deletion!";
        }
    }
}

