package com.example.methodo_de_test_eval.dto;

import jakarta.validation.constraints.*;
import java.util.Objects;


public class CreateUserDto {

    @NotBlank(message = "Le nom est obligatoire")
    private String name;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Pattern(regexp = ".*\\.com$", message = "L'email doit se terminer par .com")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 3, message = "Le mot de passe doit contenir au moins 3 caractères")
    private String password;


    public CreateUserDto() {
    }

    public CreateUserDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CreateUserDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateUserDto that = (CreateUserDto) o;
        return Objects.equals(name, that.name) && 
               Objects.equals(email, that.email) && 
               Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password);
    }
} 