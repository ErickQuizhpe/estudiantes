package com.api.estudiantes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.estudiantes.controller.dto.user.SignUpRequest;
import com.api.estudiantes.controller.dto.user.UserRequest;
import com.api.estudiantes.controller.dto.user.UserResponse;
import com.api.estudiantes.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Usuario", description = "Endpoints para la gestión de usuarios")
public class UserController {

  private final UserService userService;

  @PostMapping
  @SecurityRequirement(name = "bearerAuth")
  @Operation(summary = "Crear un nuevo usuario", description = "Crear un nuevo usuario con la información proporcionada")
  public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
    return ResponseEntity.ok(userService.createUser(request));
  }

  @PostMapping("/signup")
  @Operation(summary = "Registrar un nuevo usuario", description = "Registrar un nuevo usuario con la información proporcionada")
  public ResponseEntity<UserResponse> signUp(@RequestBody SignUpRequest request) {
    return ResponseEntity.ok(userService.signUp(request));
  }

  @GetMapping("/{id}")
  @SecurityRequirement(name = "bearerAuth")
  @Operation(summary = "Obtener usuario por ID", description = "Obtener un usuario específico por su identificador único")
  public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  @GetMapping
  @SecurityRequirement(name = "bearerAuth")
  @Operation(summary = "Obtener todos los usuarios", description = "Obtener la lista completa de todos los usuarios registrados")
  public ResponseEntity<List<UserResponse>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @PutMapping("/{id}")
  @SecurityRequirement(name = "bearerAuth")
  @Operation(summary = "Actualizar usuario", description = "Actualizar la información de un usuario existente por su ID")
  public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
    return ResponseEntity.ok(userService.updateUser(id, request));
  }

  @DeleteMapping("/{id}")
  @SecurityRequirement(name = "bearerAuth")
  @Operation(summary = "Eliminar usuario", description = "Eliminar un usuario del sistema por su ID")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

}
