package com.api.estudiantes.controller;

import java.util.List;
import java.util.Set;

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
import com.api.estudiantes.entity.studen.Studen;
import com.api.estudiantes.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User", description = "User endpoints")
public class UserController {

  private final UserService userService;

  @PostMapping
  @SecurityRequirement(name = "bearerAuth")
  @Operation(summary = "Create a new user", description = "Create a new user with the given request")
  public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
    return ResponseEntity.ok(userService.createUser(request));
  }

  @PostMapping("/signup")
  @Operation(summary = "Sign up a new user", description = "Sign up a new user with the given request")
  public ResponseEntity<UserResponse> signUp(@RequestBody SignUpRequest request) {
    return ResponseEntity.ok(userService.signUp(request));
  }

  @GetMapping("/{id}")
  @SecurityRequirement(name = "bearerAuth")
  @Operation(summary = "Get a user by id", description = "Get a user by id with the given id")
  public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  @GetMapping
  @SecurityRequirement(name = "bearerAuth")
  @Operation(summary = "Get all users", description = "Get all users")
  public ResponseEntity<List<UserResponse>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @PutMapping("/{id}")
  @SecurityRequirement(name = "bearerAuth")
  @Operation(summary = "Update a user", description = "Update a user with the given id and request")
  public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
    return ResponseEntity.ok(userService.updateUser(id, request));
  }

  @DeleteMapping("/{id}")
  @SecurityRequirement(name = "bearerAuth")
  @Operation(summary = "Delete a user", description = "Delete a user with the given id")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/favorite-students")
  @SecurityRequirement(name = "bearerAuth")
  @Operation(summary = "Add a favorite student", description = "Add a favorite student with the given id")
  public ResponseEntity<Set<Studen>> addFavoriteStudent(@PathVariable Long id) {
    return ResponseEntity.ok(userService.addFavoriteStudent(id));
  }

  @GetMapping("/favorite-students")
  @SecurityRequirement(name = "bearerAuth")
  @Operation(summary = "Get all favorite students", description = "Get all favorite students")
  public ResponseEntity<Set<Studen>> getFavoriteStudents() {
    return ResponseEntity.ok(userService.getFavoriteStudents());
  }
}
