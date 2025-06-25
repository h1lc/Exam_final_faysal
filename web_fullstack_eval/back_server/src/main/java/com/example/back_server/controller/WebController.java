package com.example.back_server.controller;

import com.example.back_server.entity.Employee;
import com.example.back_server.entity.Candidate;
import com.example.back_server.service.EmployeeService;
import com.example.back_server.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class WebController {

    private static final Logger logger = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CandidateService candidateService;

    
    @GetMapping("/dashboard")
    public String home(Model model) {
        model.addAttribute("totalEmployees", employeeService.getAllEmployees().size());
        model.addAttribute("totalCandidates", candidateService.getAllCandidates().size());
        return "home";
    }

    

    
    @GetMapping("/employees")
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employees/list";
    }

    
    @GetMapping("/employees/new")
    public String newEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employees/form";
    }

    
    @PostMapping("/employees")
    public String createEmployee(@ModelAttribute Employee employee, 
                                BindingResult result, 
                                RedirectAttributes redirectAttributes,
                                Model model) {
        logger.info("Tentative de création d'employé: {}", employee.getName());
        
        // Conversion manuelle des types
        try {
            if (employee.getNumeroIdentification() == null && employee.getNumeroIdentificationStr() != null) {
                employee.setNumeroIdentification(Long.parseLong(employee.getNumeroIdentificationStr()));
            }
            
            if (employee.getDateNaissance() == null && employee.getDateNaissanceStr() != null) {
                employee.setDateNaissance(LocalDate.parse(employee.getDateNaissanceStr()));
            }
            
            if (employee.getDebutContrat() == null && employee.getDebutContratStr() != null) {
                employee.setDebutContrat(LocalDate.parse(employee.getDebutContratStr()));
            }
            
            if (employee.getSalaire() == null && employee.getSalaireStr() != null) {
                employee.setSalaire(new BigDecimal(employee.getSalaireStr()));
            }
        } catch (NumberFormatException e) {
            logger.error("Erreur de conversion de type", e);
            redirectAttributes.addFlashAttribute("error", "Erreur de format dans les données saisies");
            model.addAttribute("employee", employee);
            return "employees/form";
        } catch (Exception e) {
            logger.error("Erreur de conversion de type", e);
            redirectAttributes.addFlashAttribute("error", "Erreur de format dans les dates");
            model.addAttribute("employee", employee);
            return "employees/form";
        }
        
        // Validation manuelle
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Le nom est obligatoire");
            model.addAttribute("employee", employee);
            return "employees/form";
        }
        
        if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "L'email est obligatoire");
            model.addAttribute("employee", employee);
            return "employees/form";
        }
        
        try {
            Employee savedEmployee = employeeService.createEmployee(employee);
            logger.info("Employé créé avec succès: {}", savedEmployee.getId());
            redirectAttributes.addFlashAttribute("success", "Employé créé avec succès !");
            return "redirect:/dashboard";
        } catch (Exception e) {
            logger.error("Erreur lors de la création de l'employé", e);
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création : " + e.getMessage());
            model.addAttribute("employee", employee);
            return "employees/form";
        }
    }

    
    @GetMapping("/employees/{id}/edit")
    public String editEmployeeForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
        
        
        if (employee.getNumeroIdentification() != null) {
            employee.setNumeroIdentificationStr(employee.getNumeroIdentification().toString());
        }
        if (employee.getDateNaissance() != null) {
            employee.setDateNaissanceStr(employee.getDateNaissance().toString());
        }
        if (employee.getDebutContrat() != null) {
            employee.setDebutContratStr(employee.getDebutContrat().toString());
        }
        if (employee.getSalaire() != null) {
            employee.setSalaireStr(employee.getSalaire().toString());
        }
        
        model.addAttribute("employee", employee);
        return "employees/form";
    }

    @PostMapping("/employees/{id}")
    public String updateEmployee(@PathVariable Long id, 
                                @ModelAttribute Employee employee, 
                                BindingResult result, 
                                RedirectAttributes redirectAttributes,
                                Model model) {
        logger.info("Tentative de mise à jour d'employé: {} (ID: {})", employee.getName(), id);
                                
        try {
            if (employee.getNumeroIdentification() == null && employee.getNumeroIdentificationStr() != null) {
                employee.setNumeroIdentification(Long.parseLong(employee.getNumeroIdentificationStr()));
            }
            
            if (employee.getDateNaissance() == null && employee.getDateNaissanceStr() != null) {
                employee.setDateNaissance(LocalDate.parse(employee.getDateNaissanceStr()));
            }
            
            if (employee.getDebutContrat() == null && employee.getDebutContratStr() != null) {
                employee.setDebutContrat(LocalDate.parse(employee.getDebutContratStr()));
            }
            
            if (employee.getSalaire() == null && employee.getSalaireStr() != null) {
                employee.setSalaire(new BigDecimal(employee.getSalaireStr()));
            }
        } catch (NumberFormatException e) {
            logger.error("Erreur de conversion de type", e);
            redirectAttributes.addFlashAttribute("error", "Erreur de format dans les données saisies");
            model.addAttribute("employee", employee);
            return "employees/form";
        } catch (Exception e) {
            logger.error("Erreur de conversion de type", e);
            redirectAttributes.addFlashAttribute("error", "Erreur de format dans les dates");
            model.addAttribute("employee", employee);
            return "employees/form";
        }
        
        
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Le nom est obligatoire");
            model.addAttribute("employee", employee);
            return "employees/form";
        }
        
        if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "L'email est obligatoire");
            model.addAttribute("employee", employee);
            return "employees/form";
        }
        
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            logger.info("Employé mis à jour avec succès: {}", updatedEmployee.getId());
            redirectAttributes.addFlashAttribute("success", "Employé mis à jour avec succès !");
            return "redirect:/dashboard";
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de l'employé", e);
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour : " + e.getMessage());
            model.addAttribute("employee", employee);
            return "employees/form";
        }
    }

    @PostMapping("/employees/{id}/delete")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.deleteEmployee(id);
            redirectAttributes.addFlashAttribute("success", "Employé supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression : " + e.getMessage());
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/employees/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
        model.addAttribute("employee", employee);
        model.addAttribute("totalEmployees", employeeService.getAllEmployees().size());
        model.addAttribute("totalCandidates", candidateService.getAllCandidates().size());
        return "employees/view";
    }

    @GetMapping("/test/create-employee")
    public String testCreateEmployee(RedirectAttributes redirectAttributes) {
        try {
            Employee testEmployee = new Employee();
            testEmployee.setName("Test Employé");
            testEmployee.setPoste("Développeur");
            testEmployee.setDebutContrat(LocalDate.now());
            testEmployee.setNumeroIdentification(12345L);
            testEmployee.setDateNaissance(LocalDate.of(1990, 1, 1));
            testEmployee.setAddress("123 Rue Test");
            testEmployee.setEmail("test@example.com");
            testEmployee.setTelephone("0123456789");
            testEmployee.setSalaire(new BigDecimal("3000.00"));
            
            Employee savedEmployee = employeeService.createEmployee(testEmployee);
            logger.info("Test employé créé avec succès: {}", savedEmployee.getId());
            redirectAttributes.addFlashAttribute("success", "Test employé créé avec succès ! ID: " + savedEmployee.getId());
        } catch (Exception e) {
            logger.error("Erreur lors de la création du test employé", e);
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création du test employé : " + e.getMessage());
        }
        return "redirect:/dashboard";
    }

    

    
    @GetMapping("/candidates")
    public String listCandidates(Model model) {
        List<Candidate> candidates = candidateService.getAllCandidates();
        model.addAttribute("candidates", candidates);
        return "candidates/list";
    }

    
    @GetMapping("/candidates/new")
    public String newCandidateForm(Model model) {
        model.addAttribute("candidate", new Candidate());
        return "candidates/form";
    }

    @PostMapping("/candidates")
    public String createCandidate(@ModelAttribute Candidate candidate, 
                                 BindingResult result, 
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        logger.info("Tentative de création de candidat: {}", candidate.getName());
        
        try {
            if (candidate.getNumeroIdentification() == null && candidate.getNumeroIdentificationStr() != null) {
                candidate.setNumeroIdentification(Long.parseLong(candidate.getNumeroIdentificationStr()));
            }
            
            if (candidate.getDateNaissance() == null && candidate.getDateNaissanceStr() != null) {
                candidate.setDateNaissance(LocalDate.parse(candidate.getDateNaissanceStr()));
            }
            
            if (candidate.getDateEntretien() == null && candidate.getDateEntretienStr() != null) {
                candidate.setDateEntretien(LocalDate.parse(candidate.getDateEntretienStr()));
            }
            
            if (candidate.getNote() == null && candidate.getNoteStr() != null) {
                candidate.setNote(Integer.parseInt(candidate.getNoteStr()));
            }
        } catch (NumberFormatException e) {
            logger.error("Erreur de conversion de type", e);
            redirectAttributes.addFlashAttribute("error", "Erreur de format dans les données saisies");
            model.addAttribute("candidate", candidate);
            return "candidates/form";
        } catch (Exception e) {
            logger.error("Erreur de conversion de type", e);
            redirectAttributes.addFlashAttribute("error", "Erreur de format dans les dates");
            model.addAttribute("candidate", candidate);
            return "candidates/form";
        }
        
        if (candidate.getName() == null || candidate.getName().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Le nom est obligatoire");
            model.addAttribute("candidate", candidate);
            return "candidates/form";
        }
        
        if (candidate.getEmail() == null || candidate.getEmail().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "L'email est obligatoire");
            model.addAttribute("candidate", candidate);
            return "candidates/form";
        }
        
        try {
            Candidate savedCandidate = candidateService.createCandidate(candidate);
            logger.info("Candidat créé avec succès: {}", savedCandidate.getId());
            redirectAttributes.addFlashAttribute("success", "Candidat créé avec succès !");
            return "redirect:/dashboard";
        } catch (Exception e) {
            logger.error("Erreur lors de la création du candidat", e);
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création : " + e.getMessage());
            model.addAttribute("candidate", candidate);
            return "candidates/form";
        }
    }

    @GetMapping("/candidates/{id}/edit")
    public String editCandidateForm(@PathVariable Long id, Model model) {
        Candidate candidate = candidateService.getCandidateById(id)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé"));
        
        if (candidate.getNumeroIdentification() != null) {
            candidate.setNumeroIdentificationStr(candidate.getNumeroIdentification().toString());
        }
        if (candidate.getDateNaissance() != null) {
            candidate.setDateNaissanceStr(candidate.getDateNaissance().toString());
        }
        if (candidate.getDateEntretien() != null) {
            candidate.setDateEntretienStr(candidate.getDateEntretien().toString());
        }
        if (candidate.getNote() != null) {
            candidate.setNoteStr(candidate.getNote().toString());
        }
        
        model.addAttribute("candidate", candidate);
        return "candidates/form";
    }


    @PostMapping("/candidates/{id}")
    public String updateCandidate(@PathVariable Long id, 
                                 @ModelAttribute Candidate candidate, 
                                 BindingResult result, 
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        logger.info("Tentative de mise à jour de candidat: {} (ID: {})", candidate.getName(), id);
        
        try {
            if (candidate.getNumeroIdentification() == null && candidate.getNumeroIdentificationStr() != null) {
                candidate.setNumeroIdentification(Long.parseLong(candidate.getNumeroIdentificationStr()));
            }
            
            if (candidate.getDateNaissance() == null && candidate.getDateNaissanceStr() != null) {
                candidate.setDateNaissance(LocalDate.parse(candidate.getDateNaissanceStr()));
            }
            
            if (candidate.getDateEntretien() == null && candidate.getDateEntretienStr() != null) {
                candidate.setDateEntretien(LocalDate.parse(candidate.getDateEntretienStr()));
            }
            
            if (candidate.getNote() == null && candidate.getNoteStr() != null) {
                candidate.setNote(Integer.parseInt(candidate.getNoteStr()));
            }
        } catch (NumberFormatException e) {
            logger.error("Erreur de conversion de type", e);
            redirectAttributes.addFlashAttribute("error", "Erreur de format dans les données saisies");
            model.addAttribute("candidate", candidate);
            return "candidates/form";
        } catch (Exception e) {
            logger.error("Erreur de conversion de type", e);
            redirectAttributes.addFlashAttribute("error", "Erreur de format dans les dates");
            model.addAttribute("candidate", candidate);
            return "candidates/form";
        }
        
        if (candidate.getName() == null || candidate.getName().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Le nom est obligatoire");
            model.addAttribute("candidate", candidate);
            return "candidates/form";
        }
        
        if (candidate.getEmail() == null || candidate.getEmail().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "L'email est obligatoire");
            model.addAttribute("candidate", candidate);
            return "candidates/form";
        }
        
        try {
            Candidate updatedCandidate = candidateService.updateCandidate(id, candidate);
            logger.info("Candidat mis à jour avec succès: {}", updatedCandidate.getId());
            redirectAttributes.addFlashAttribute("success", "Candidat mis à jour avec succès !");
            return "redirect:/dashboard";
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour du candidat", e);
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour : " + e.getMessage());
            model.addAttribute("candidate", candidate);
            return "candidates/form";
        }
    }

    @PostMapping("/candidates/{id}/delete")
    public String deleteCandidate(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            candidateService.deleteCandidate(id);
            redirectAttributes.addFlashAttribute("success", "Candidat supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression : " + e.getMessage());
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/candidates/{id}")
    public String viewCandidate(@PathVariable Long id, Model model) {
        Candidate candidate = candidateService.getCandidateById(id)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé"));
        model.addAttribute("candidate", candidate);
        model.addAttribute("totalEmployees", employeeService.getAllEmployees().size());
        model.addAttribute("totalCandidates", candidateService.getAllCandidates().size());
        return "candidates/view";
    }

    
    @GetMapping("/test/create-candidate")
    public String testCreateCandidate(RedirectAttributes redirectAttributes) {
        try {
            Candidate testCandidate = new Candidate();
            testCandidate.setName("Test Candidat");
            testCandidate.setNumeroIdentification(54321L);
            testCandidate.setDateNaissance(LocalDate.of(1995, 5, 15));
            testCandidate.setAddress("456 Avenue Test");
            testCandidate.setEmail("test.candidat@example.com");
            testCandidate.setTelephone("0987654321");
            testCandidate.setNote(8);
            testCandidate.setSpecialite("Java");
            testCandidate.setDateEntretien(LocalDate.now().plusDays(7));
            
            Candidate savedCandidate = candidateService.createCandidate(testCandidate);
            logger.info("Test candidat créé avec succès: {}", savedCandidate.getId());
            redirectAttributes.addFlashAttribute("success", "Test candidat créé avec succès ! ID: " + savedCandidate.getId());
        } catch (Exception e) {
            logger.error("Erreur lors de la création du test candidat", e);
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création du test candidat : " + e.getMessage());
        }
        return "redirect:/dashboard";
    }
} 