package com.example.methodo_de_test_eval.repository;

import com.example.methodo_de_test_eval.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    

    Optional<User> findByEmail(String email);
    

    boolean existsByEmail(String email);
    

    Optional<User> findByName(String name);
} 