package com.example.back_server.repository;

import com.example.back_server.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    // Recherche par nom
    List<Candidate> findByNameContainingIgnoreCase(String name);

    // Recherche par email
    Optional<Candidate> findByEmail(String email);

    // Recherche par numéro d'identification
    Optional<Candidate> findByNumeroIdentification(Long numeroIdentification);

    // Recherche par spécialité
    List<Candidate> findBySpecialiteContainingIgnoreCase(String specialite);

    // Recherche par note minimum
    List<Candidate> findByNoteGreaterThanEqual(Integer minNote);

    // Recherche par note maximum
    List<Candidate> findByNoteLessThanEqual(Integer maxNote);

    // Recherche par plage de notes
    List<Candidate> findByNoteBetween(Integer minNote, Integer maxNote);

    // Recherche par date d'entretien
    List<Candidate> findByDateEntretien(LocalDate dateEntretien);

    // Recherche par période d'entretien
    @Query("SELECT c FROM Candidate c WHERE c.dateEntretien BETWEEN :startDate AND :endDate")
    List<Candidate> findCandidatesByInterviewPeriod(@Param("startDate") LocalDate startDate, 
                                                   @Param("endDate") LocalDate endDate);

    // Recherche des candidats avec une note élevée (8+)
    @Query("SELECT c FROM Candidate c WHERE c.note >= 8 ORDER BY c.note DESC")
    List<Candidate> findTopCandidates();

    // Recherche par spécialité et note minimum
    @Query("SELECT c FROM Candidate c WHERE LOWER(c.specialite) LIKE LOWER(CONCAT('%', :specialite, '%')) AND c.note >= :minNote")
    List<Candidate> findBySpecialiteAndMinNote(@Param("specialite") String specialite, 
                                              @Param("minNote") Integer minNote);

    // Recherche des candidats récents (entretien dans les 30 derniers jours)
    @Query("SELECT c FROM Candidate c WHERE c.dateEntretien >= :recentDate ORDER BY c.dateEntretien DESC")
    List<Candidate> findRecentCandidates(@Param("recentDate") LocalDate recentDate);

    // Vérifier si un email existe déjà
    boolean existsByEmail(String email);

    // Vérifier si un numéro d'identification existe déjà
    boolean existsByNumeroIdentification(Long numeroIdentification);

    // Compter les candidats par spécialité
    @Query("SELECT c.specialite, COUNT(c) FROM Candidate c GROUP BY c.specialite")
    List<Object[]> countCandidatesBySpecialite();

    // Moyenne des notes par spécialité
    @Query("SELECT c.specialite, AVG(c.note) FROM Candidate c GROUP BY c.specialite")
    List<Object[]> averageNoteBySpecialite();
} 