package com.project.Healthcare.service;

import com.project.Healthcare.model.Patient;
import com.project.Healthcare.repository.PatientRepository;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class DataRetrievalService {

    private static final Logger log = LoggerFactory.getLogger(DataRetrievalService.class);

    @Autowired
    private PatientRepository patientRepository;

    // Cache the result of this method. Cache key is automatically the method name
    @Cacheable(value = "patients")
    public List<Patient> getAllPatient() {
        log.info("Fetching all patients from the database.");
        List<Patient> patients = patientRepository.findAll();
        log.info("Retrieved {} patients.", patients.size());
        return patients;
    }

    // Cache the result of findById using the patient ID as the cache key
    @Cacheable(value = "patient", key = "#id")
    public Patient findById(Long id) {
        log.info("Fetching patient with ID: {}", id);
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Patient not found with ID: {}", id);
                    return new EntityNotFoundException("Patient not found");
                });
        Hibernate.initialize(patient.getMedications());
        log.info("Successfully retrieved patient: {}", patient);
        return patient;
    }

    // Update the cache after saving a patient
    @CachePut(value = "patient", key = "#patient.id")
    public Patient savePatient(Patient patient) {
        log.info("Saving patient: {}", patient);
        Patient savedPatient = patientRepository.save(patient);
        log.info("Patient saved: {}", savedPatient);
        return savedPatient;
    }

    // Evict the cache for the patient with the given ID
    @CacheEvict(value = "patient", key = "#id")
    public void deletePatient(Long id) {
        log.info("Deleting patient with ID: {}", id);
        patientRepository.deleteById(id);
        log.info("Patient with ID: {} has been deleted.", id);
    }
}
