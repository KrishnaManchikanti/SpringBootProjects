package com.project.Healthcare.controller;

import com.project.Healthcare.model.Patient;
import com.project.Healthcare.repository.PatientRepository;
import com.project.Healthcare.service.DataExporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patients/export")
public class DataExportController {
    private final PatientRepository patientRepository;
    private final DataExporterService dataExporterService;
    @Autowired
    public DataExportController(PatientRepository patientRepository, DataExporterService dataExporterService) {
        this.patientRepository = patientRepository;
        this.dataExporterService = dataExporterService;
    }

    @GetMapping("/csv")
    public ResponseEntity<String> exportPatientsToCSV() {
        List<Patient> patients = patientRepository.findAll();
        dataExporterService.savePatientsToCSV(patients, "patients.csv");
        return ResponseEntity.ok("Patients exported to CSV successfully.");
    }

    @GetMapping("/json")
    public ResponseEntity<String> exportPatientsToJson() {
        List<Patient> patients = patientRepository.findAll();
        dataExporterService.savePatientsToJson(patients, "patients.json");
        return ResponseEntity.ok("Patients exported to JSON successfully.");
    }

}
