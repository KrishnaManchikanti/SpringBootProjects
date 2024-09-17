package com.project.Healthcare.service;

import com.project.Healthcare.model.MedicalData;
import com.project.Healthcare.repository.MedicalDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaEventsConsumer {

    @Autowired
    LargePayloadService largePayloadService;

    @Autowired
    MedicalDataRepository medicalDataRepository;

    @KafkaListener(topics = "customTopic", groupId = "jt-custom-group")
    @CachePut(value = "medicalData", key = "#patientId.id")
    public void consumeEvents(MedicalData medicalData) {
        log.info("Consumer consume the events {}", medicalData.toString());

        MedicalData entity= new MedicalData(
                medicalData.getPatientId(),
                medicalData.getDoctorId(),
                medicalData.getDiagnosis(),
                medicalData.getCreatedAt()
        );

        log.info("Processing...LargePayload");
        largePayloadService.processLargePayload(entity);

    }

    @Cacheable(value = "medicalData",key = "#id")
    public MedicalData getMedicalData(Long id){
        log.info("id {}",id);
        log.info(String.valueOf(medicalDataRepository.findById(id).get()));
        return medicalDataRepository.findById(id).get();
    }

}