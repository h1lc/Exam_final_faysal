package com.example.methodo_de_test_eval.service;

import com.example.methodo_de_test_eval.entity.User;
import com.example.methodo_de_test_eval.exception.DataIntegrityViolationException;
import com.example.methodo_de_test_eval.exception.ObjectNotFoundException;
import com.example.methodo_de_test_eval.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("L'ID de l'utilisateur doit être valide et positif");
        }
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("L'email ne peut pas être null ou vide");
        }
        return userRepository.findByEmail(email.trim());
    }

    @Override
    public User createUser(User user) {
        validateUserData(user);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DataIntegrityViolationException("Un utilisateur avec cet email existe déjà: " + user.getEmail());
        }
        
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("L'ID de l'utilisateur doit être valide et positif");
        }
        validateUserData(userDetails);
        
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Utilisateur non trouvé avec l'ID: " + id));

        if (!existingUser.getEmail().equals(userDetails.getEmail()) && 
            userRepository.existsByEmail(userDetails.getEmail())) {
            throw new DataIntegrityViolationException("Un utilisateur avec cet email existe déjà: " + userDetails.getEmail());
        }

        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPassword(userDetails.getPassword());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("L'ID de l'utilisateur doit être valide et positif");
        }
        
        if (!userRepository.existsById(id)) {
            throw new ObjectNotFoundException("Utilisateur non trouvé avec l'ID: " + id);
        }
        
        userRepository.deleteById(id);
    }

    @Override
    public boolean userExists(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        return userRepository.existsById(id);
    }

    @Override
    public boolean emailExists(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return userRepository.existsByEmail(email.trim());
    }

    private void validateUserData(User user) {
        if (user == null) {
            throw new IllegalArgumentException("L'utilisateur ne peut pas être null");
        }
        
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'utilisateur est obligatoire");
        }
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("L'email de l'utilisateur est obligatoire");
        }
        
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe de l'utilisateur est obligatoire");
        }
        
        if (user.getPassword().length() < 3) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 3 caractères");
        }
        
        if (!user.getEmail().toLowerCase().endsWith(".com")) {
            throw new IllegalArgumentException("L'email doit se terminer par .com");
        }
    }
} 