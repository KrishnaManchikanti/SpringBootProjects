package com.project.Healthcare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String specialization;
    private String phNo;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Consultation> consultations;
}

