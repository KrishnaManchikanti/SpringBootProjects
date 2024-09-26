package com.project.Healthcare.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import com.project.Healthcare.exception.ErrorExportingJsonData;
import com.project.Healthcare.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class DataExporterService {

    @Autowired
    private ObjectMapper objectMapper;

    public void savePatientsToJson(List<Patient> patients, String fileName) {
        try {
            objectMapper.writeValue(new File(fileName),patients);
            log.info("Patients saved to JSON file successfully!");
        } catch (IOException e) {
            log.error("Failed to save Patients to JSON file!");
            throw new ErrorExportingJsonData("Failed to save Patients to JSON file");
        }
    }

    public void savePatientsToCSV(List<Patient> patients, String fileName) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(fileName))){
            String[] header = { "ID", "Name", "Age", "Address", "Phone", "Doctor ID", "Insurance ID" };
            csvWriter.writeNext(header);

            for (Patient patient : patients) {
                String[] data = {
                        String.valueOf(patient.getId()),
                        patient.getName(),
                        String.valueOf(patient.getAge()),
                        patient.getAddress(),
                        patient.getPhNo(),
                        String.valueOf(patient.getDoctor()),
                        String.valueOf(patient.getInsurance())
                };
                csvWriter.writeNext(data);
                log.info("Patients saved to CSV file successfully!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
