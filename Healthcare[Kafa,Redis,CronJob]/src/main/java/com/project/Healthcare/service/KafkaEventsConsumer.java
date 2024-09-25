package com.project.Healthcare.service;

import com.project.Healthcare.model.Patient;
import com.project.Healthcare.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaEventsConsumer {

    private final PatientRepository patientRepository;

    @Autowired
    public KafkaEventsConsumer(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @KafkaListener(topics = "${app.topic.name}", groupId = "patient-data")
    public void consumePatientData(Patient patient) {
        log.info("Received patient data: {}", patient);

        try {
            // Save patient data to the repository
            patientRepository.save(patient);
            log.info("Successfully saved patient data: {}", patient.getId());
        } catch (Exception e) {
            log.error("Failed to save patient data: {}", patient, e);
        }
    }
}
