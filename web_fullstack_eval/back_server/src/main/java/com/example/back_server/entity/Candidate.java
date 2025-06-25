package com.example.back_server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "candidates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Column(name = "name", nullable = false)
    private String name;

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

    @NotNull(message = "La note est obligatoire")
    @Column(name = "note", nullable = false)
    private Integer note;

    @NotBlank(message = "La spécialité est obligatoire")
    @Column(name = "specialite", nullable = false)
    private String specialite;

    @NotNull(message = "La date de l'entretien est obligatoire")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_entretien", nullable = false)
    private LocalDate dateEntretien;

    @Column(name = "observation", length = 500)
    private String observation;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private java.time.LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private java.time.LocalDateTime updatedAt;

    // Champs temporaires pour le formulaire (non persistés)
    @Transient
    private String numeroIdentificationStr;
    
    @Transient
    private String dateNaissanceStr;
    
    @Transient
    private String dateEntretienStr;
    
    @Transient
    private String noteStr;

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