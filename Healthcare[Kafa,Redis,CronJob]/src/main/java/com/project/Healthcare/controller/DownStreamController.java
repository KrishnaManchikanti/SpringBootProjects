package com.project.Healthcare.controller;

import com.project.Healthcare.model.Patient;
import com.project.Healthcare.repository.PatientRepository;
import com.project.Healthcare.service.KafkaEventsProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/downStreamData")
public class DownStreamController {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    KafkaEventsProducer kafkaEventsProducer;

    @GetMapping("/toTopic")
    public void sendDataToDownStream(){
        List<Patient> patientList= patientRepository.findAll();
        kafkaEventsProducer.sendPatientDataToDownstream(patientList);
    }
}
