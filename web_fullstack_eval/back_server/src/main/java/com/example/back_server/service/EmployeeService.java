package com.example.back_server.service;

import com.example.back_server.entity.Employee;
import com.example.back_server.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Créer un nouvel employé
     */
    public Employee createEmployee(Employee employee) {
        // Vérifier si l'email existe déjà
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException("Un employé avec cet email existe déjà");
        }
        
        // Vérifier si le numéro d'identification existe déjà
        if (employeeRepository.existsByNumeroIdentification(employee.getNumeroIdentification())) {
            throw new RuntimeException("Un employé avec ce numéro d'identification existe déjà");
        }
        
        return employeeRepository.save(employee);
    }

    /**
     * Récupérer tous les employés
     */
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Récupérer un employé par son ID
     */
    @Transactional(readOnly = true)
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    /**
     * Mettre à jour un employé
     */
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé avec l'ID: " + id));

        // Vérifier si le nouvel email existe déjà (sauf pour l'employé actuel)
        if (!employee.getEmail().equals(employeeDetails.getEmail()) && 
            employeeRepository.existsByEmail(employeeDetails.getEmail())) {
            throw new RuntimeException("Un employé avec cet email existe déjà");
        }

        // Vérifier si le nouveau numéro d'identification existe déjà (sauf pour l'employé actuel)
        if (!employee.getNumeroIdentification().equals(employeeDetails.getNumeroIdentification()) && 
            employeeRepository.existsByNumeroIdentification(employeeDetails.getNumeroIdentification())) {
            throw new RuntimeException("Un employé avec ce numéro d'identification existe déjà");
        }

        // Mettre à jour les champs
        employee.setName(employeeDetails.getName());
        employee.setPoste(employeeDetails.getPoste());
        employee.setDebutContrat(employeeDetails.getDebutContrat());
        employee.setFinContrat(employeeDetails.getFinContrat());
        employee.setNumeroIdentification(employeeDetails.getNumeroIdentification());
        employee.setDateNaissance(employeeDetails.getDateNaissance());
        employee.setAddress(employeeDetails.getAddress());
        employee.setEmail(employeeDetails.getEmail());
        employee.setTelephone(employeeDetails.getTelephone());
        employee.setSalaire(employeeDetails.getSalaire());
        employee.setObservation(employeeDetails.getObservation());

        return employeeRepository.save(employee);
    }

    /**
     * Supprimer un employé
     */
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé avec l'ID: " + id));
        
        employeeRepository.delete(employee);
    }

    /**
     * Rechercher des employés par nom
     */
    @Transactional(readOnly = true)
    public List<Employee> searchEmployeesByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Rechercher des employés par poste
     */
    @Transactional(readOnly = true)
    public List<Employee> searchEmployeesByPoste(String poste) {
        return employeeRepository.findByPosteContainingIgnoreCase(poste);
    }

    /**
     * Récupérer les employés actifs
     */
    @Transactional(readOnly = true)
    public List<Employee> getActiveEmployees() {
        return employeeRepository.findActiveEmployees(LocalDate.now());
    }

    /**
     * Rechercher des employés par salaire minimum
     */
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByMinSalary(java.math.BigDecimal minSalary) {
        return employeeRepository.findBySalaireGreaterThanEqual(minSalary);
    }

    /**
     * Vérifier si un employé existe par email
     */
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    /**
     * Vérifier si un employé existe par numéro d'identification
     */
    @Transactional(readOnly = true)
    public boolean existsByNumeroIdentification(Long numeroIdentification) {
        return employeeRepository.existsByNumeroIdentification(numeroIdentification);
    }
} 