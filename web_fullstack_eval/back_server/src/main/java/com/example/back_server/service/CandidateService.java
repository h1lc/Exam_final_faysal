package com.example.back_server.service;

import com.example.back_server.entity.Candidate;
import com.example.back_server.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    /**
     * Créer un nouveau candidat
     */
    public Candidate createCandidate(Candidate candidate) {
        // Vérifier si l'email existe déjà
        if (candidateRepository.existsByEmail(candidate.getEmail())) {
            throw new RuntimeException("Un candidat avec cet email existe déjà");
        }
        
        // Vérifier si le numéro d'identification existe déjà
        if (candidateRepository.existsByNumeroIdentification(candidate.getNumeroIdentification())) {
            throw new RuntimeException("Un candidat avec ce numéro d'identification existe déjà");
        }
        
        return candidateRepository.save(candidate);
    }

    /**
     * Récupérer tous les candidats
     */
    @Transactional(readOnly = true)
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    /**
     * Récupérer un candidat par son ID
     */
    @Transactional(readOnly = true)
    public Optional<Candidate> getCandidateById(Long id) {
        return candidateRepository.findById(id);
    }

    /**
     * Mettre à jour un candidat
     */
    public Candidate updateCandidate(Long id, Candidate candidateDetails) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé avec l'ID: " + id));

        // Vérifier si le nouvel email existe déjà (sauf pour le candidat actuel)
        if (!candidate.getEmail().equals(candidateDetails.getEmail()) && 
            candidateRepository.existsByEmail(candidateDetails.getEmail())) {
            throw new RuntimeException("Un candidat avec cet email existe déjà");
        }

        // Vérifier si le nouveau numéro d'identification existe déjà (sauf pour le candidat actuel)
        if (!candidate.getNumeroIdentification().equals(candidateDetails.getNumeroIdentification()) && 
            candidateRepository.existsByNumeroIdentification(candidateDetails.getNumeroIdentification())) {
            throw new RuntimeException("Un candidat avec ce numéro d'identification existe déjà");
        }

        // Mettre à jour les champs
        candidate.setName(candidateDetails.getName());
        candidate.setNumeroIdentification(candidateDetails.getNumeroIdentification());
        candidate.setDateNaissance(candidateDetails.getDateNaissance());
        candidate.setAddress(candidateDetails.getAddress());
        candidate.setEmail(candidateDetails.getEmail());
        candidate.setTelephone(candidateDetails.getTelephone());
        candidate.setNote(candidateDetails.getNote());
        candidate.setSpecialite(candidateDetails.getSpecialite());
        candidate.setDateEntretien(candidateDetails.getDateEntretien());
        candidate.setObservation(candidateDetails.getObservation());

        return candidateRepository.save(candidate);
    }

    /**
     * Supprimer un candidat
     */
    public void deleteCandidate(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé avec l'ID: " + id));
        
        candidateRepository.delete(candidate);
    }

    /**
     * Rechercher des candidats par nom
     */
    @Transactional(readOnly = true)
    public List<Candidate> searchCandidatesByName(String name) {
        return candidateRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Rechercher des candidats par spécialité
     */
    @Transactional(readOnly = true)
    public List<Candidate> searchCandidatesBySpecialite(String specialite) {
        return candidateRepository.findBySpecialiteContainingIgnoreCase(specialite);
    }

    /**
     * Récupérer les candidats avec une note élevée (8+)
     */
    @Transactional(readOnly = true)
    public List<Candidate> getTopCandidates() {
        return candidateRepository.findTopCandidates();
    }

    /**
     * Rechercher des candidats par plage de notes
     */
    @Transactional(readOnly = true)
    public List<Candidate> getCandidatesByNoteRange(Integer minNote, Integer maxNote) {
        return candidateRepository.findByNoteBetween(minNote, maxNote);
    }

    /**
     * Rechercher des candidats par note minimum
     */
    @Transactional(readOnly = true)
    public List<Candidate> getCandidatesByMinNote(Integer minNote) {
        return candidateRepository.findByNoteGreaterThanEqual(minNote);
    }

    /**
     * Rechercher des candidats par date d'entretien
     */
    @Transactional(readOnly = true)
    public List<Candidate> getCandidatesByInterviewDate(LocalDate date) {
        return candidateRepository.findByDateEntretien(date);
    }

    /**
     * Rechercher des candidats récents (entretien dans les 30 derniers jours)
     */
    @Transactional(readOnly = true)
    public List<Candidate> getRecentCandidates() {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        return candidateRepository.findRecentCandidates(thirtyDaysAgo);
    }

    /**
     * Rechercher des candidats par spécialité et note minimum
     */
    @Transactional(readOnly = true)
    public List<Candidate> getCandidatesBySpecialiteAndMinNote(String specialite, Integer minNote) {
        return candidateRepository.findBySpecialiteAndMinNote(specialite, minNote);
    }

    /**
     * Vérifier si un candidat existe par email
     */
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return candidateRepository.existsByEmail(email);
    }

    /**
     * Vérifier si un candidat existe par numéro d'identification
     */
    @Transactional(readOnly = true)
    public boolean existsByNumeroIdentification(Long numeroIdentification) {
        return candidateRepository.existsByNumeroIdentification(numeroIdentification);
    }

    /**
     * Obtenir les statistiques par spécialité
     */
    @Transactional(readOnly = true)
    public List<Object[]> getCandidatesCountBySpecialite() {
        return candidateRepository.countCandidatesBySpecialite();
    }

    /**
     * Obtenir la moyenne des notes par spécialité
     */
    @Transactional(readOnly = true)
    public List<Object[]> getAverageNoteBySpecialite() {
        return candidateRepository.averageNoteBySpecialite();
    }
} 