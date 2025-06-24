package com.example.methodo_de_test_eval.service;

import com.example.methodo_de_test_eval.entity.User;
import com.example.methodo_de_test_eval.exception.DataIntegrityViolationException;
import com.example.methodo_de_test_eval.exception.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    /**
     * Crée un nouvel utilisateur
     * @param user l'utilisateur à créer
     * @return l'utilisateur créé
     * @throws DataIntegrityViolationException si l'email existe déjà
     */
    User createUser(User user);

    /**
     * Met à jour un utilisateur existant
     * @param id l'ID de l'utilisateur à mettre à jour
     * @param userDetails les nouvelles données de l'utilisateur
     * @return l'utilisateur mis à jour
     * @throws ObjectNotFoundException si l'utilisateur n'existe pas
     * @throws DataIntegrityViolationException si l'email existe déjà
     */
    User updateUser(Long id, User userDetails);

    /**
     * Supprime un utilisateur
     * @param id l'ID de l'utilisateur à supprimer
     * @throws ObjectNotFoundException si l'utilisateur n'existe pas
     */
    void deleteUser(Long id);

    /**
     * Vérifie si un utilisateur existe
     * @param id l'ID de l'utilisateur
     * @return true si l'utilisateur existe
     */
    boolean userExists(Long id);

    /**
     * Vérifie si un email existe déjà
     * @param email l'email à vérifier
     * @return true si l'email existe déjà
     */
    boolean emailExists(String email);
} 