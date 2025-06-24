package com.example.methodo_de_test_eval.service;

import com.example.methodo_de_test_eval.dto.CreateUserDto;
import com.example.methodo_de_test_eval.dto.UpdateUserDto;
import com.example.methodo_de_test_eval.dto.UserDto;
import com.example.methodo_de_test_eval.exception.DataIntegrityViolationException;
import com.example.methodo_de_test_eval.exception.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> getAllUsers();

    Optional<UserDto> getUserById(Long id);

    Optional<UserDto> getUserByEmail(String email);

    /**
     * Crée un nouvel utilisateur
     * @param createUserDto les données de l'utilisateur à créer
     * @return l'utilisateur créé (sans mot de passe)
     * @throws DataIntegrityViolationException si l'email existe déjà
     */
    UserDto createUser(CreateUserDto createUserDto);

    /**
     * Met à jour un utilisateur existant
     * @param id l'ID de l'utilisateur à mettre à jour
     * @param updateUserDto les nouvelles données de l'utilisateur
     * @return l'utilisateur mis à jour (sans mot de passe)
     * @throws ObjectNotFoundException si l'utilisateur n'existe pas
     * @throws DataIntegrityViolationException si l'email existe déjà
     */
    UserDto updateUser(Long id, UpdateUserDto updateUserDto);

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