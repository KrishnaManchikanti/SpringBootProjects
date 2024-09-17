package com.project.Healthcare.controller;

import com.project.Healthcare.model.MedicalData;
import com.project.Healthcare.service.KafkaEventsConsumer;
import com.project.Healthcare.service.KafkaEventsProducer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingest")
@Validated
public class EventController {

    @Autowired
    private KafkaEventsProducer kafkaMessagePublisher;

    @Autowired
    private KafkaEventsConsumer kafkaEventsConsumer;

    @GetMapping("/publish/{message}")
    public ResponseEntity<?> publishMessage(@PathVariable String message) {
        try {
            for (int i = 0; i <= 100000; i++) {
                kafkaMessagePublisher.sendMessageToTopic(message + " : " + i);
            }
            return ResponseEntity.ok("message published successfully ..");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PostMapping("/publish")
    public void publishEvent(@Valid @RequestBody MedicalData medicalData) {
        kafkaMessagePublisher.sendEventsToTopic(medicalData);
    }

    @GetMapping("/getMedicalData/{id}")
    public MedicalData getMedicalData(@PathVariable Long id){
        return kafkaEventsConsumer.getMedicalData(id);
    }
}
