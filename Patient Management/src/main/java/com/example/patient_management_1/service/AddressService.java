package com.example.patient_management_1.service;

import com.example.patient_management_1.entity.Address;
import com.example.patient_management_1.entity.Patient;
import com.example.patient_management_1.repository.AddressRepository;
import com.example.patient_management_1.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    PatientRepository patientRepository;

    public Address getAddress(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    public Address createAddress(Long patientId,Address address) {
        Patient patient = patientRepository.findById(patientId).get();
        Address savedAddress = addressRepository.save(address);
        patient.setAddress(address);
        patientRepository.save(patient);
        return savedAddress;
    }

    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        for(Patient patient : patientRepository.findAll()) {
            if (patient.getAddress()!=null && patient.getAddress().getId().equals(id)) {
                patient.setAddress(null);
                patientRepository.save(patient);
                addressRepository.deleteById(id);
            }
        }
    }
}
