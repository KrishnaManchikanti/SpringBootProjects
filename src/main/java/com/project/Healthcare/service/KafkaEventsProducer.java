package com.project.Healthcare.service;

import com.project.Healthcare.model.MedicalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaEventsProducer {

    @Autowired
    private KafkaTemplate<MedicalData, Object> kafkaTemplate;

    public void sendEventsToTopic(MedicalData medicalData) {

        try {
            CompletableFuture<SendResult<MedicalData, Object>> future = kafkaTemplate.send("customTopic", medicalData);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + medicalData.toString() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                            medicalData.toString() + "] due to : " + ex.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
        }
    }

    public void sendMessageToTopic(String message){
        CompletableFuture<SendResult<MedicalData, Object>> future = kafkaTemplate.send("javatechie-demo2", message);
        future.whenComplete((result,ex)->{
            if (ex == null) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });

    }
}
