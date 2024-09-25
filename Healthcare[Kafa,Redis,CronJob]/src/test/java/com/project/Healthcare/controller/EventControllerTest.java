package com.project.Healthcare.controller;

import com.project.Healthcare.model.Consultation;
import com.project.Healthcare.service.KafkaEventsConsumer;
import com.project.Healthcare.service.KafkaEventsProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private KafkaEventsProducer kafkaMessagePublisher;

    @Mock
    private KafkaEventsConsumer kafkaEventsConsumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testPublishMessage_Exception() {
        doThrow(new RuntimeException("Kafka error")).when(kafkaMessagePublisher).sendMessageToTopic(anyString());

        ResponseEntity<?> response = eventController.publishMessage("testMessage");
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testPublishEvent() {
        Consultation consultation = new Consultation();
        // Set up medicalData fields as necessary for your test

        eventController.publishEvent(consultation);

        // Verify that sendEventsToTopic was called with the correct medicalData
        verify(kafkaMessagePublisher, times(1)).sendEventsToTopic(consultation);
    }

    @Test
    void testGetMedicalData() {
        Long medicalDataId = 1L;
        Consultation consultation = new Consultation();
        // Set up medicalData fields as necessary for your test
        when(kafkaEventsConsumer.getMedicalData(medicalDataId)).thenReturn(consultation);

        Consultation result = eventController.getMedicalData(medicalDataId);

        assertEquals(consultation, result);
        verify(kafkaEventsConsumer, times(1)).getMedicalData(medicalDataId);
    }
}
