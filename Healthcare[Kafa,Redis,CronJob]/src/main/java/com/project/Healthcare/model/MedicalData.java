package com.project.Healthcare.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MedicalData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Patient ID cannot be null")
    private String patientId;

    @NotNull(message = "Doctor ID cannot be null")
    private String doctorId;

    @NotNull(message = "Diagnosis cannot be null")
    @Size(min = 5, message = "Diagnosis should have at least 5 characters")
    private String diagnosis;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;


    public MedicalData(String patientId, String doctorId, String diagnosis, LocalDateTime createdAt) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
        this.createdAt = createdAt;
    }
}
