package com.example.patient_management_1.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "specialty")
    private String specialty;

    public String getSpecialty() {
        return specialty;
    }

    public Doctor(String name, String specialty) {
        this.name = name;
        this.specialty = specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

// getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Doctor() {
    }
}

