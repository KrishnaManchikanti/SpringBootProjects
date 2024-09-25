package com.project.Healthcare.controller;

import com.project.Healthcare.model.Patient;
import com.project.Healthcare.service.DataRetrievalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/cache")
@Slf4j
public class CacheController {

    private final DataRetrievalService dataRetrievalService;
    private final CacheManager cacheManager;

    @Autowired
    public CacheController(DataRetrievalService dataRetrievalService, CacheManager cacheManager) {
        this.dataRetrievalService = dataRetrievalService;
        this.cacheManager = cacheManager;
    }

    // Endpoint to retrieve all patients (with caching)
    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatients() {
        log.info("Retrieving all patients from cache or database.");
        List<Patient> patients = dataRetrievalService.getAllPatient();
        return ResponseEntity.ok(patients);
    }

    // Endpoint to retrieve a specific patient by ID (with caching)
    @GetMapping("/patient/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        log.info("Retrieving patient with ID: {}", id);
        Patient patient = dataRetrievalService.findById(id);
        return ResponseEntity.ok(patient);
    }

    // Endpoint to update a patient in the cache (force a cache update)
    @PutMapping("/patient/{id}")
    public ResponseEntity<Patient> updatePatientCache(@PathVariable Long id, @RequestBody Patient patient) {
        log.info("Updating patient with ID: {}", id);
        Patient updatedPatient = dataRetrievalService.savePatient(patient);
        return ResponseEntity.ok(updatedPatient);
    }

    // Endpoint to clear a specific patient cache entry
    @DeleteMapping("/patient/{id}/evict")
    public ResponseEntity<String> evictPatientCache(@PathVariable Long id) {
        log.info("Evicting patient cache for ID: {}", id);
        Objects.requireNonNull(cacheManager.getCache("patient")).evict(id);
        return ResponseEntity.ok("Patient cache evicted for ID: " + id);
    }

    // Endpoint to clear all patient caches
    @DeleteMapping("/patients/evict")
    public ResponseEntity<String> evictAllPatientsCache() {
        log.info("Evicting all patient caches.");
        Objects.requireNonNull(cacheManager.getCache("patients")).clear();
        return ResponseEntity.ok("All patient caches evicted");
    }
}
