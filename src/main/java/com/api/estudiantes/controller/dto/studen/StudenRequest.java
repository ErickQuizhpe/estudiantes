package com.api.estudiantes.controller.dto.studen;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudenRequest(
    @NotBlank(message = "La matrícula es obligatoria")
    String matricula,
    
    @NotBlank(message = "El teléfono es obligatorio")
    String telefono,
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    LocalDate fechaNacimiento,
    
    @NotBlank(message = "La carrera es obligatoria")
    String carrera,
    
    @NotNull(message = "El semestre es obligatorio")
    Integer semestre,
    
    @NotNull(message = "La fecha de ingreso es obligatoria")
    LocalDate fechaIngreso,
    
    Boolean activo,
    
    // Datos del usuario asociado
    @NotBlank(message = "El nombre es obligatorio")
    String firstName,
    
    @NotBlank(message = "El apellido es obligatorio")
    String lastName,
    
    @NotBlank(message = "El nombre de usuario es obligatorio")
    String username,
    
    @Email(message = "El email debe ser válido")
    @NotBlank(message = "El email es obligatorio")
    String email,
    
    String password
) {
}
