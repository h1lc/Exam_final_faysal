package com.example.back_server.controller;

import com.example.back_server.entity.Candidate;
import com.example.back_server.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidates")
@CrossOrigin(origins = "*")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

   
    @PostMapping
    public ResponseEntity<?> createCandidate(@Valid @RequestBody Candidate candidate) {
        try {
            Candidate createdCandidate = candidateService.createCandidate(candidate);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCandidate);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

   
    @GetMapping
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        List<Candidate> candidates = candidateService.getAllCandidates();
        return ResponseEntity.ok(candidates);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        Optional<Candidate> candidate = candidateService.getCandidateById(id);
        return candidate.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCandidate(@PathVariable Long id, @Valid @RequestBody Candidate candidateDetails) {
        try {
            Candidate updatedCandidate = candidateService.updateCandidate(id, candidateDetails);
            return ResponseEntity.ok(updatedCandidate);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCandidate(@PathVariable Long id) {
        try {
            candidateService.deleteCandidate(id);
            return ResponseEntity.ok().body("Candidat supprimé avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

   
    @GetMapping("/search/name")
    public ResponseEntity<List<Candidate>> searchCandidatesByName(@RequestParam String name) {
        List<Candidate> candidates = candidateService.searchCandidatesByName(name);
        return ResponseEntity.ok(candidates);
    }

   
    @GetMapping("/search/specialite")
    public ResponseEntity<List<Candidate>> searchCandidatesBySpecialite(@RequestParam String specialite) {
        List<Candidate> candidates = candidateService.searchCandidatesBySpecialite(specialite);
        return ResponseEntity.ok(candidates);
    }

   
    @GetMapping("/top")
    public ResponseEntity<List<Candidate>> getTopCandidates() {
        List<Candidate> candidates = candidateService.getTopCandidates();
        return ResponseEntity.ok(candidates);
    }

   
    @GetMapping("/search/notes")
    public ResponseEntity<List<Candidate>> getCandidatesByNoteRange(
            @RequestParam Integer minNote, 
            @RequestParam Integer maxNote) {
        List<Candidate> candidates = candidateService.getCandidatesByNoteRange(minNote, maxNote);
        return ResponseEntity.ok(candidates);
    }

   
    @GetMapping("/search/min-note")
    public ResponseEntity<List<Candidate>> getCandidatesByMinNote(@RequestParam Integer minNote) {
        List<Candidate> candidates = candidateService.getCandidatesByMinNote(minNote);
        return ResponseEntity.ok(candidates);
    }

    
    @GetMapping("/search/interview-date")
    public ResponseEntity<List<Candidate>> getCandidatesByInterviewDate(@RequestParam String date) {
        LocalDate interviewDate = LocalDate.parse(date);
        List<Candidate> candidates = candidateService.getCandidatesByInterviewDate(interviewDate);
        return ResponseEntity.ok(candidates);
    }

    
    @GetMapping("/recent")
    public ResponseEntity<List<Candidate>> getRecentCandidates() {
        List<Candidate> candidates = candidateService.getRecentCandidates();
        return ResponseEntity.ok(candidates);
    }

    
    @GetMapping("/search/specialite-note")
    public ResponseEntity<List<Candidate>> getCandidatesBySpecialiteAndMinNote(
            @RequestParam String specialite, 
            @RequestParam Integer minNote) {
        List<Candidate> candidates = candidateService.getCandidatesBySpecialiteAndMinNote(specialite, minNote);
        return ResponseEntity.ok(candidates);
    }

    
    @GetMapping("/check/email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = candidateService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    
    @GetMapping("/check/numero")
    public ResponseEntity<Boolean> checkNumeroExists(@RequestParam Long numero) {
        boolean exists = candidateService.existsByNumeroIdentification(numero);
        return ResponseEntity.ok(exists);
    }

    
    @GetMapping("/stats/count-by-specialite")
    public ResponseEntity<List<Object[]>> getCandidatesCountBySpecialite() {
        List<Object[]> stats = candidateService.getCandidatesCountBySpecialite();
        return ResponseEntity.ok(stats);
    }

    
    @GetMapping("/stats/average-note-by-specialite")
    public ResponseEntity<List<Object[]>> getAverageNoteBySpecialite() {
        List<Object[]> stats = candidateService.getAverageNoteBySpecialite();
        return ResponseEntity.ok(stats);
    }

    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Candidate Controller is running!");
    }
} 