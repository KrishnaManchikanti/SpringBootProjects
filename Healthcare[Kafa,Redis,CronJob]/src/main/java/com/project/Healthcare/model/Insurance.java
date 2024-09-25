package com.project.Healthcare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Insurance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String patient_id;
    private String provider;
    private String policy_number;
    private Date valid_until;

    @OneToOne(mappedBy = "insurance", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Patient patient;
}
