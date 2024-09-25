package com.project.Healthcare.config;

import com.project.Healthcare.service.StaleDataDeletionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StaleDataDeletionJob {

    private final StaleDataDeletionService staleDataDeletionService;

    // Run the job at midnight daily
    // Uncomment the line below to schedule it for daily execution
    // @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "0 * * * * ?")  // Runs every minute for testing purposes
    public void run() {
        log.info("Starting scheduled task to delete stale data...");
        try {
            staleDataDeletionService.deleteStaleData();
            log.info("Scheduled task completed successfully.");
        } catch (Exception e) {
            log.error("Error during scheduled stale data deletion", e);
        }
    }
}
