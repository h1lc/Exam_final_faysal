package com.example.back_server.repository;

import com.example.back_server.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Recherche par nom
    List<Employee> findByNameContainingIgnoreCase(String name);

    // Recherche par poste
    List<Employee> findByPosteContainingIgnoreCase(String poste);

    // Recherche par email
    Optional<Employee> findByEmail(String email);

    // Recherche par numéro d'identification
    Optional<Employee> findByNumeroIdentification(Long numeroIdentification);

    // Recherche des employés avec contrat actif (fin de contrat null ou future)
    @Query("SELECT e FROM Employee e WHERE e.finContrat IS NULL OR e.finContrat >= :currentDate")
    List<Employee> findActiveEmployees(@Param("currentDate") LocalDate currentDate);

    // Recherche des employés par période de contrat
    @Query("SELECT e FROM Employee e WHERE e.debutContrat BETWEEN :startDate AND :endDate")
    List<Employee> findEmployeesByContractPeriod(@Param("startDate") LocalDate startDate, 
                                                @Param("endDate") LocalDate endDate);

    // Recherche par salaire minimum
    List<Employee> findBySalaireGreaterThanEqual(java.math.BigDecimal minSalaire);

    // Recherche par spécialité (poste)
    @Query("SELECT e FROM Employee e WHERE LOWER(e.poste) LIKE LOWER(CONCAT('%', :specialite, '%'))")
    List<Employee> findBySpecialite(@Param("specialite") String specialite);

    // Vérifier si un email existe déjà
    boolean existsByEmail(String email);

    // Vérifier si un numéro d'identification existe déjà
    boolean existsByNumeroIdentification(Long numeroIdentification);
} 