package com.project.Healthcare.controller;

import com.project.Healthcare.model.Patient;
import com.project.Healthcare.service.CsvReaderService;
import com.project.Healthcare.service.JsonReaderService;
import com.project.Healthcare.service.KafkaEventsProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class IngestControllerTest {

    // Mock the dependencies
    @Mock
    private JsonReaderService jsonReaderService;

    @Mock
    private CsvReaderService csvReaderService;

    @Mock
    private KafkaEventsProducer kafkaEventsProducer;

    // Inject the mocks into the controller
    @InjectMocks
    private IngestController ingestController;

    @BeforeEach
    public void setUp() {
        // Initialize the mocks
        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIngestJsonDataSuccess() throws Exception {
        String jsonData = "[{\"id\": 1, \"name\": \"John Doe\"}]";
        CompletableFuture<String> completableFuture = CompletableFuture.completedFuture("JSON data ingested successfully");

        // Mock the readJson method
        when(jsonReaderService.readJson(anyString())).thenReturn(completableFuture);

        ResponseEntity<String> response = ingestController.ingestJsonData(jsonData);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("JSON data ingested successfully", response.getBody());
        verify(jsonReaderService, times(1)).readJson(jsonData);
    }

    @Test
    public void testIngestJsonDataFailure() throws Exception {
        String jsonData = "dummyString";
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.completeExceptionally(new RuntimeException("Error reading JSON"));

        // Mock the readJson method
        when(jsonReaderService.readJson(anyString())).thenReturn(completableFuture);

        ResponseEntity<String> response = ingestController.ingestJsonData(jsonData);

        assertEquals(400, response.getStatusCode().value());
        assertEquals(response.getBody(),"Invalid JSON format. JSON must start with '[' and end with ']', or start with '{' and end with '}'.");
    }

    @Test
    public void testIngestCsvSuccess() throws Exception {
        // Create a mock patient list
        List<Patient> mockPatients = List.of(new Patient(1L, "John Doe", 30, "123 Main St", "1234567890", null, null));

        // Mock the readCsv method
        CompletableFuture<List<Patient>> patientListFuture = CompletableFuture.completedFuture(mockPatients);
        when(csvReaderService.readCsv()).thenReturn(patientListFuture);

        ResponseEntity<String> response = ingestController.ingestCsv();

        assertEquals(200, response.getStatusCode().value());
        assertEquals("CSV data reading and ingestion initiated.", response.getBody());

        // Verify that each patient is sent to Kafka
        verify(kafkaEventsProducer, times(mockPatients.size())).sendPatientToTopic(any(Patient.class));
    }

    @Test
    public void testIngestCsvFailure() throws Exception {
        CompletableFuture<List<Patient>> patientListFuture = new CompletableFuture<>();
        patientListFuture.completeExceptionally(new RuntimeException("Error reading CSV"));

        // Mock the readCsv method
        when(csvReaderService.readCsv()).thenReturn(patientListFuture);

        ResponseEntity<String> response = ingestController.ingestCsv();

        assertEquals(500, response.getStatusCode().value());
        assertTrue(response.getBody().contains("Error ingesting CSV data"));
    }
}
