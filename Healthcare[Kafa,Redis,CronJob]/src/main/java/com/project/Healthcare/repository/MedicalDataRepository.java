package com.project.Healthcare.repository;

import com.project.Healthcare.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MedicalDataRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByCreatedAtBefore(LocalDateTime timestamp);
}
