package com.project.Healthcare.repository;

import com.project.Healthcare.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {
    @Query(value = "SELECT p.* FROM patient p JOIN insurance i ON p.insurance_id = i.id WHERE i.provider = :insuranceProvider", nativeQuery = true)
    List<Patient> getPatientByInsuranceProvider(@Param("insuranceProvider") String insuranceProvider);
}
