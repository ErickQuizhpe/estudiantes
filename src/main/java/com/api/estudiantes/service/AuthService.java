package com.api.estudiantes.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.api.estudiantes.controller.dto.auth.LoginRequest;
import com.api.estudiantes.controller.dto.auth.LoginResponse;
import com.api.estudiantes.controller.dto.user.UserResponse;
import com.api.estudiantes.entity.user.UserEntity;
import com.api.estudiantes.repository.UserRepository;
import com.api.estudiantes.utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final JwtUtils jwtUtils;

  public LoginResponse loginUser(LoginRequest loginRequest) {
    // Autenticar usuario
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.username(),
            loginRequest.password()
        )
    );

    // Obtener detalles del usuario autenticado
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    
    // Buscar el usuario en la base de datos
    UserEntity user = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    // Actualizar último acceso
    user.actualizarUltimoAcceso();
    userRepository.save(user);

    // Generar token JWT
    String token = jwtUtils.createToken(authentication);

    // Crear UserResponse
    UserResponse userResponse = new UserResponse(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getFirstName(),
        user.getLastName(),
        user.isEnabled(),
        user.getRoles().stream()
            .map(role -> role.getName().name())
            .collect(java.util.stream.Collectors.toSet())
    );

    return new LoginResponse(userResponse, token);
  }
}
