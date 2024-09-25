package com.project.Healthcare.controller;

import com.project.Healthcare.model.Patient;
import com.project.Healthcare.service.DataRetrievalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    @GetMapping("/patients")
    public ResponseEntity<Page<Patient>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer age) {

        Pageable pageable = PageRequest.of(page, size);
        Specification<Patient> spec = filterByAge(age);
        log.info("Retrieving all patients from cache or database.");
        Page<Patient> patients = dataRetrievalService.getAllPatient(pageable, spec);
        return ResponseEntity.ok(patients);
    }

    // Filtering by age
    private Specification<Patient> filterByAge(Integer age) {
        return (root, query, builder) -> {
            if (age != null) {
                return builder.equal(root.get("age"), age);
            }
            return builder.conjunction();
        };
    }

    /**
     *
     * @param insuranceProvider as PathVariable
     * @return Patient by Insurance Provider using NativeQuery
     */
    @GetMapping("/provider/{insuranceProvider}")
    public ResponseEntity<List<Patient>> getPatientByInsuranceProvider(@PathVariable String insuranceProvider) {
        List<Patient> patients = dataRetrievalService.getPatientByInsuranceProvider(insuranceProvider);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        log.info("Retrieving patient with ID: {}", id);
        Patient patient = dataRetrievalService.findById(id);
        return ResponseEntity.ok(patient);
    }

    @PutMapping("/patient/{id}")
    public ResponseEntity<Patient> updatePatientCache(@PathVariable Long id, @RequestBody Patient patient) {
        log.info("Updating patient with ID: {}", id);
        Patient updatedPatient = dataRetrievalService.savePatient(patient);
        return ResponseEntity.ok(updatedPatient);
    }


    @DeleteMapping("/patient/{id}/evict")
    public ResponseEntity<String> evictPatientCache(@PathVariable Long id) {
        log.info("Evicting patient cache for ID: {}", id);
        Objects.requireNonNull(cacheManager.getCache("patient")).evict(id);
        return ResponseEntity.ok("Patient cache evicted for ID: " + id);
    }


    @DeleteMapping("/patients/evict")
    public ResponseEntity<String> evictAllPatientsCache() {
        log.info("Evicting all patient caches.");
        Objects.requireNonNull(cacheManager.getCache("patients")).clear();
        return ResponseEntity.ok("All patient caches evicted");
    }
}
