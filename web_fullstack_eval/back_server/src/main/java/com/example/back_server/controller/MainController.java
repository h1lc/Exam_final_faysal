package com.example.back_server.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class MainController {

    @GetMapping("/")
    public RedirectView redirectToDashboard() {
        return new RedirectView("/dashboard");
    }

    @GetMapping("/api")
    public Map<String, Object> getApiInfo() {
        Map<String, Object> apiInfo = new HashMap<>();
        apiInfo.put("message", "API de gestion des employés et candidats");
        apiInfo.put("version", "1.0.0");
        apiInfo.put("status", "running");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("employees", "/api/employees");
        endpoints.put("candidates", "/api/candidates");
        endpoints.put("h2-console", "/h2-console");
        apiInfo.put("endpoints", endpoints);
        
        Map<String, String> documentation = new HashMap<>();
        documentation.put("employees_health", "GET /api/employees/health");
        documentation.put("candidates_health", "GET /api/candidates/health");
        documentation.put("create_employee", "POST /api/employees");
        documentation.put("create_candidate", "POST /api/candidates");
        documentation.put("get_all_employees", "GET /api/employees");
        documentation.put("get_all_candidates", "GET /api/candidates");
        documentation.put("update_employee", "PUT /api/employees/{id}");
        documentation.put("update_candidate", "PUT /api/candidates/{id}");
        documentation.put("delete_employee", "DELETE /api/employees/{id}");
        documentation.put("delete_candidate", "DELETE /api/candidates/{id}");
        apiInfo.put("documentation", documentation);
        
        return apiInfo;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("message", "Application is running successfully!");
        return health;
    }
} 