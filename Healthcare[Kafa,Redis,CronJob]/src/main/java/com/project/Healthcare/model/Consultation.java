package com.project.Healthcare.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Consultation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    @JsonBackReference
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private Doctor doctor;

    @NotNull(message = "HealthCheckup cannot be null")
    @Enumerated(EnumType.STRING)
    private HealthCheckup healthCheckup;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medication_id")
    private Medication medication;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    public Consultation(Patient patient, Doctor doctor, HealthCheckup healthCheckup, LocalDateTime createdAt) {
        this.patient = patient;
        this.doctor = doctor;
        this.healthCheckup = healthCheckup;
        this.createdAt = createdAt;
    }

}
