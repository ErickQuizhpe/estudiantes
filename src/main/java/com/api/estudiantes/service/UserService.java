package com.api.estudiantes.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.estudiantes.controller.dto.user.SignUpRequest;
import com.api.estudiantes.controller.dto.user.UserResponse;
import com.api.estudiantes.entity.user.RoleName;
import com.api.estudiantes.entity.user.UserEntity;
import com.api.estudiantes.entity.studen.Studen;
import com.api.estudiantes.repository.UserRepository;
import com.api.estudiantes.repository.RoleRepository;
import com.api.estudiantes.controller.dto.user.UserRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public UserResponse createUser(UserRequest request) {
    // Verificar que el usuario no exista
    if (userRepository.existsByUsername(request.username())) {
      throw new RuntimeException("El nombre de usuario ya existe");
    }
    if (userRepository.existsByEmail(request.email())) {
      throw new RuntimeException("El email ya está registrado");
    }

    // Crear la entidad usuario
    UserEntity user = UserEntity.builder()
        .firstName(request.firstName())
        .lastName(request.lastName())
        .username(request.username())
        .email(request.email())
        .password(passwordEncoder.encode(request.password()))
        .enabled(true)
        .roles(Set.of(roleRepository.findByName(RoleName.ESTUDIANTE)
            .orElseThrow(() -> new RuntimeException("Rol ESTUDIANTE no encontrado"))))
        .build();

    UserEntity savedUser = userRepository.save(user);
    return mapToUserResponse(savedUser);
  }

  public UserResponse signUp(SignUpRequest request) {
    // Verificar que el usuario no exista
    if (userRepository.existsByUsername(request.username())) {
      throw new RuntimeException("El nombre de usuario ya existe");
    }
    if (userRepository.existsByEmail(request.email())) {
      throw new RuntimeException("El email ya está registrado");
    }

    // Crear la entidad usuario
    UserEntity user = UserEntity.builder()
        .firstName("") // Sin firstName en SignUpRequest
        .lastName("") // Sin lastName en SignUpRequest
        .username(request.username())
        .email(request.email())
        .password(passwordEncoder.encode(request.password()))
        .enabled(true)
        .roles(Set.of(roleRepository.findByName(RoleName.ESTUDIANTE)
            .orElseThrow(() -> new RuntimeException("Rol ESTUDIANTE no encontrado"))))
        .build();

    UserEntity savedUser = userRepository.save(user);
    return mapToUserResponse(savedUser);
  }

  public UserResponse getUserById(Long id) {
    UserEntity user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    return mapToUserResponse(user);
  }

  public List<UserResponse> getAllUsers() {
    return userRepository.findAll().stream()
        .map(this::mapToUserResponse)
        .collect(Collectors.toList());
  }

  public UserResponse updateUser(Long id, UserRequest request) {
    UserEntity user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    // Verificar duplicados si se cambia username o email
    if (!user.getUsername().equals(request.username()) && 
        userRepository.existsByUsername(request.username())) {
      throw new RuntimeException("El nombre de usuario ya existe");
    }
    if (!user.getEmail().equals(request.email()) && 
        userRepository.existsByEmail(request.email())) {
      throw new RuntimeException("El email ya está registrado");
    }

    // Actualizar campos
    user.setFirstName(request.firstName());
    user.setLastName(request.lastName());
    user.setUsername(request.username());
    user.setEmail(request.email());
    
    if (request.password() != null && !request.password().isEmpty()) {
      user.setPassword(passwordEncoder.encode(request.password()));
    }

    UserEntity updatedUser = userRepository.save(user);
    return mapToUserResponse(updatedUser);
  }

  public void deleteUser(Long id) {
    if (!userRepository.existsById(id)) {
      throw new RuntimeException("Usuario no encontrado");
    }
    userRepository.deleteById(id);
  }

  // Métodos relacionados con estudiantes favoritos (en lugar de recipes)
  public Set<Studen> addFavoriteStudent(Long studentId) {
    String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
    // Validar que el usuario actual existe
    userRepository.findByUsername(currentUsername)
        .orElseThrow(() -> new RuntimeException("Usuario actual no encontrado"));
    
    // Aquí iría la lógica para agregar estudiante favorito
    // Por ahora retornamos un set vacío
    return Set.of();
  }

  public Set<Studen> getFavoriteStudents() {
    String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
    // Validar que el usuario actual existe
    userRepository.findByUsername(currentUsername)
        .orElseThrow(() -> new RuntimeException("Usuario actual no encontrado"));
    
    // Aquí iría la lógica para obtener estudiantes favoritos
    // Por ahora retornamos un set vacío
    return Set.of();
  }

  private UserResponse mapToUserResponse(UserEntity user) {
    return new UserResponse(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getFirstName(),
        user.getLastName(),
        user.isEnabled(),
        user.getRoles().stream()
            .map(role -> role.getName().name())
            .collect(Collectors.toSet())
    );
  }
}
