package com.example.patient_management_1.service;

import com.example.patient_management_1.entity.Doctor;
import com.example.patient_management_1.entity.Patient;
import com.example.patient_management_1.repository.DoctorRepository;
import com.example.patient_management_1.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PatientRepository patientRepository;
    public Doctor getDoctor(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public Doctor createDoctor(Long patientId,Doctor doctor) {
        Patient patient = patientRepository.findById(patientId).get();
        Doctor savedDoctor = doctorRepository.save(doctor);
        patient.setDoctor(doctor);
        patientRepository.save(patient);
        return savedDoctor;
    }

    public Doctor updateDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        for(Patient patient : patientRepository.findAll()) {
            if (patient.getDoctor()!=null && patient.getDoctor().getId().equals(id)) {
                patient.setDoctor(null);
                patientRepository.save(patient);
                doctorRepository.deleteById(id);
            }
        }
    }

}
