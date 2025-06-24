package com.example.methodo_de_test_eval.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Objects;


@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Pattern(regexp = ".*\\.com$", message = "L'email doit se terminer par .com")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 3, message = "Le mot de passe doit contenir au moins 3 caractères")
    @Column(name = "password", nullable = false)
    private String password;

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    //@Override
    //public String toString() {
    //    return "User{" +
    //            "id=" + id +
    //            ", name='" + name + '\'' +
    //            ", email='" + email + '\'' +
    //            ", password='[PROTECTED]'" +
    //            '}';
    //}
//
    //@Override
    //public boolean equals(Object o) {
    //    if (this == o) return true;
    //    if (o == null || getClass() != o.getClass()) return false;
    //    User user = (User) o;
    //    return Objects.equals(id, user.id) && 
    //           Objects.equals(name, user.name) && 
    //           Objects.equals(email, user.email);
    //}
//
    //@Override
    //public int hashCode() {
    //    return Objects.hash(id, name, email);
    //}
} 