package com.project.Healthcare.service;

import com.project.Healthcare.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class KafkaEventsProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.topic.name}")
    private String topicName;

    @Autowired
    public KafkaEventsProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPatientToTopic(Patient patient) {
        log.info("Preparing to send patient data to Kafka topic: {}", topicName);

        CompletableFuture<SendResult<String, Object>> completableFuture = kafkaTemplate.send(topicName, patient);
        completableFuture.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[{}] with offset=[{}]", patient, result.getRecordMetadata().offset());
            } else {
                log.error("Unable to send message=[{}] due to: {}", patient, ex.getMessage(), ex);
            }
        });
    }


    @Async
    public void sendPatientDataToDownstream(List<Patient> patientList) {
        log.info("Preparing to send patient data to DownStream using Kafka topic: {}", "downStreamTopic");

        CompletableFuture<SendResult<String, Object>> completableFuture = kafkaTemplate.send("downStreamTopic", patientList);
        completableFuture.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Successful in SendingData to DownStream topic with name: {}",result.getRecordMetadata().topic());
            } else {
                log.error("Successful in SendingData to DownStream{}", ex.getMessage(), ex);
            }
        });
    }
}
