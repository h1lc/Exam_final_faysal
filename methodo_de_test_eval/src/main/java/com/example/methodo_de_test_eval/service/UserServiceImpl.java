package com.example.methodo_de_test_eval.service;

import com.example.methodo_de_test_eval.dto.CreateUserDto;
import com.example.methodo_de_test_eval.dto.UpdateUserDto;
import com.example.methodo_de_test_eval.dto.UserDto;
import com.example.methodo_de_test_eval.entity.User;
import com.example.methodo_de_test_eval.exception.DataIntegrityViolationException;
import com.example.methodo_de_test_eval.exception.ObjectNotFoundException;
import com.example.methodo_de_test_eval.repository.UserRepository;
import com.example.methodo_de_test_eval.util.UserMapper;
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
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserMapper.toDtoList(users);
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("L'ID de l'utilisateur doit être valide et positif");
        }
        Optional<User> user = userRepository.findById(id);
        return user.map(UserMapper::toDto);
    }

    @Override
    public Optional<UserDto> getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("L'email ne peut pas être null ou vide");
        }
        Optional<User> user = userRepository.findByEmail(email.trim());
        return user.map(UserMapper::toDto);
    }

    @Override
    public UserDto createUser(CreateUserDto createUserDto) {
        validateCreateUserData(createUserDto);
        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new DataIntegrityViolationException("Un utilisateur avec cet email existe déjà: " + createUserDto.getEmail());
        }
        
        User user = UserMapper.fromCreateDto(createUserDto);
        User savedUser = userRepository.save(user);
        return UserMapper.toDto(savedUser);
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserDto updateUserDto) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("L'ID de l'utilisateur doit être valide et positif");
        }
        validateUpdateUserData(updateUserDto);
        
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Utilisateur non trouvé avec l'ID: " + id));

        if (!existingUser.getEmail().equals(updateUserDto.getEmail()) && 
            userRepository.existsByEmail(updateUserDto.getEmail())) {
            throw new DataIntegrityViolationException("Un utilisateur avec cet email existe déjà: " + updateUserDto.getEmail());
        }

        UserMapper.updateUserFromDto(existingUser, updateUserDto);
        User savedUser = userRepository.save(existingUser);
        return UserMapper.toDto(savedUser);
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

    private void validateCreateUserData(CreateUserDto createUserDto) {
        if (createUserDto == null) {
            throw new IllegalArgumentException("Les données de l'utilisateur ne peuvent pas être null");
        }
        
        if (createUserDto.getName() == null || createUserDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'utilisateur est obligatoire");
        }
        
        if (createUserDto.getEmail() == null || createUserDto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("L'email de l'utilisateur est obligatoire");
        }
        
        if (createUserDto.getPassword() == null || createUserDto.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe de l'utilisateur est obligatoire");
        }
        
        if (createUserDto.getPassword().length() < 3) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 3 caractères");
        }
        
        if (!createUserDto.getEmail().toLowerCase().endsWith(".com")) {
            throw new IllegalArgumentException("L'email doit se terminer par .com");
        }
    }

    private void validateUpdateUserData(UpdateUserDto updateUserDto) {
        if (updateUserDto == null) {
            throw new IllegalArgumentException("Les données de mise à jour ne peuvent pas être null");
        }
        
        if (updateUserDto.getName() == null || updateUserDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'utilisateur est obligatoire");
        }
        
        if (updateUserDto.getEmail() == null || updateUserDto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("L'email de l'utilisateur est obligatoire");
        }
        
        if (updateUserDto.getPassword() != null && updateUserDto.getPassword().length() < 3) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 3 caractères");
        }
        
        if (!updateUserDto.getEmail().toLowerCase().endsWith(".com")) {
            throw new IllegalArgumentException("L'email doit se terminer par .com");
        }
    }
} 