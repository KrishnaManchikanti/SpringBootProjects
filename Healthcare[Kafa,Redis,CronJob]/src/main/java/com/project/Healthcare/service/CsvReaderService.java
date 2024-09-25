package com.project.Healthcare.service;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.project.Healthcare.exception.ErrorReadingCsvException;
import com.project.Healthcare.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class CsvReaderService {

    @Async
    public CompletableFuture<List<Patient>> readCsv() {
        log.info("Starting to read CSV file...");

        ClassPathResource csvFile = new ClassPathResource("patients_with_10000_records.csv");
        List<Patient> patients;

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(csvFile.getInputStream()));
             CSVReader csvReader = new CSVReader(bf)) {

            CsvToBean<Patient> csvToBean = new CsvToBeanBuilder<Patient>(csvReader).withType(Patient.class).build();
            patients = csvToBean.parse();

            log.info("Successfully read CSV file: {} with {} records", csvFile.getFilename(), patients.size());
            return CompletableFuture.completedFuture(patients);

        } catch (IOException e) {
            log.error("Error reading the CSV file: {}", csvFile.getFilename(), e);
            throw new ErrorReadingCsvException("Failed to read CSV file: " + csvFile.getFilename()+" "+ e);
        } finally {
            log.info("Finished reading CSV file: {}", csvFile.getFilename());
        }
    }
}
