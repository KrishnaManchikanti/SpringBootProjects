package com.project.Healthcare.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.Healthcare.exception.ErrorReadingJsonException;
import com.project.Healthcare.model.Consultation;
import com.project.Healthcare.model.Insurance;
import com.project.Healthcare.model.Medication;
import com.project.Healthcare.model.Patient;
import com.project.Healthcare.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class JsonReaderService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ConsultationRepository consultationRepository;
    private final InsuranceRepository insuranceRepository;
    private final MedicationRepository medicationRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public JsonReaderService(PatientRepository patientRepository,
                             DoctorRepository doctorRepository,
                             ConsultationRepository consultationRepository,
                             InsuranceRepository insuranceRepository,
                             MedicationRepository medicationRepository,
                             ObjectMapper objectMapper) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.consultationRepository = consultationRepository;
        this.insuranceRepository = insuranceRepository;
        this.medicationRepository = medicationRepository;

        this.objectMapper = objectMapper;
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Async
    public CompletableFuture<String> readJson(String patientsData) {
        long startTime = System.currentTimeMillis();
        log.info("Starting to read JSON data.");

        try {
            // Parse the JSON string into a List of Patient objects
            List<Patient> patients = objectMapper.readValue(patientsData, new TypeReference<List<Patient>>() {});

            // Filter patients by phone number validity
            patients = patients.stream()
                    .filter(patient -> isValidPhoneNumber(patient.getPhNo()))
                    .filter(patient -> patient.getPhNo().length() == 10)
                    .toList();

            log.info("Parsed {} valid patients from JSON.", patients.size());

            for (Patient patient : patients) {
                savePatientData(patient);
            }

        } catch (Exception e) {
            log.error("Failed to parse JSON data: {}", e.getMessage(), e);
            throw new ErrorReadingJsonException("Failed to parse JSON " +e);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info("Execution time: {} seconds", duration / 1000.0);

        return CompletableFuture.completedFuture("Reading JSON file successful");
    }

    private boolean isValidPhoneNumber(String phNo) {
        return phNo != null && phNo.chars().allMatch(Character::isDigit);
    }

    private void savePatientData(Patient patient) {
        log.info("Saving patient: {}", patient);

        doctorRepository.save(patient.getDoctor());
        patientRepository.save(patient);

        Insurance insurance = patient.getInsurance();
        insurance.setPatient_id(String.valueOf(patient.getId()));
        insuranceRepository.save(insurance);

        for (Consultation consultation : patient.getConsultations()) {
            consultation.setPatient(patient);
            consultation.setDoctor(patient.getDoctor());
            consultation.setMedication(patient.getMedications().get(0));
            consultationRepository.save(consultation);
        }

        for (Medication medication : patient.getMedications()) {
            medication.setPatient(patient);
            medicationRepository.save(medication);
        }

        log.info("Successfully saved patient and associated data for ID: {}", patient.getId());
    }
}
