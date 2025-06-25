package com.example.back_server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Le poste est obligatoire")
    @Column(name = "poste", nullable = false)
    private String poste;

    @NotNull(message = "La date de début de contrat est obligatoire")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "debut_contrat", nullable = false)
    private LocalDate debutContrat;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fin_contrat")
    private LocalDate finContrat;

    @NotNull(message = "Le numéro d'identification est obligatoire")
    @Column(name = "numero_identification", nullable = false, unique = true)
    private Long numeroIdentification;

    @NotNull(message = "La date de naissance est obligatoire")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @NotBlank(message = "L'adresse est obligatoire")
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Le téléphone est obligatoire")
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @NotNull(message = "Le salaire est obligatoire")
    @Column(name = "salaire", nullable = false, precision = 10, scale = 2)
    private BigDecimal salaire;

    @Column(name = "observation", length = 500)
    private String observation;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private java.time.LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private java.time.LocalDateTime updatedAt;


    @Transient
    private String numeroIdentificationStr;
    
    @Transient
    private String dateNaissanceStr;
    
    @Transient
    private String debutContratStr;
    
    @Transient
    private String salaireStr;

    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        updatedAt = java.time.LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now();
    }
} 