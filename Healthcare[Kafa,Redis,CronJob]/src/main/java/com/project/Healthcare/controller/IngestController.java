package com.project.Healthcare.controller;

import com.project.Healthcare.model.Patient;
import com.project.Healthcare.service.CsvReaderService;
import com.project.Healthcare.service.JsonReaderService;
import com.project.Healthcare.service.KafkaEventsProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/ingest")
@Slf4j
public class IngestController {

    private final JsonReaderService jsonReaderService;
    private final CsvReaderService csvReaderService;
    private final KafkaEventsProducer kafkaEventsProducer;

    @Autowired
    public IngestController(JsonReaderService jsonReaderService, CsvReaderService csvReaderService, KafkaEventsProducer kafkaEventsProducer) {
        this.jsonReaderService = jsonReaderService;
        this.csvReaderService = csvReaderService;
        this.kafkaEventsProducer = kafkaEventsProducer;
    }

    @PostMapping("/jsonData")
    public ResponseEntity<String> ingestJsonData(@RequestBody String patientsData) {
        log.info("Ingesting JSON data.");
        CompletableFuture<String> completableFuture = jsonReaderService.readJson(patientsData);

        try {
            String result = completableFuture.get();
            log.info("Successfully ingested JSON data.");
            return ResponseEntity.ok(result);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Failed to ingest JSON data", e);
            return ResponseEntity.status(500).body("Error ingesting JSON data: " + e.getMessage());
        }
    }

    @GetMapping("/csv")
    public ResponseEntity<String> ingestCsv() {
        log.info("Ingesting CSV data.");
        CompletableFuture<List<Patient>> patientListFuture = csvReaderService.readCsv();

        try {
            List<Patient> patientList = patientListFuture.get();
            patientList.forEach(patient -> {
                kafkaEventsProducer.sendPatientToTopic(patient);
                log.info("Sent patient with ID {} to Kafka topic.", patient.getId());
            });
            log.info("Successfully ingested CSV data.");
        } catch (InterruptedException | ExecutionException e) {
            log.error("Failed to ingest CSV data", e);
            return ResponseEntity.status(500).body("Error ingesting CSV data: " + e.getMessage());
        }

        return ResponseEntity.ok("CSV data reading and ingestion initiated.");
    }
}
