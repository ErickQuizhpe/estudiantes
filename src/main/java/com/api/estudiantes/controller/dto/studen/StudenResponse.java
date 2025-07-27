package com.api.estudiantes.controller.dto.studen;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record StudenResponse(
    Long id,
    String matricula,
    String telefono,
    LocalDate fechaNacimiento,
    String carrera,
    Integer semestre,
    LocalDate fechaIngreso,
    Boolean activo,
    
    // Datos del usuario asociado
    Long usuarioId,
    String firstName,
    String lastName,
    String username,
    String email,
    LocalDateTime fechaCreacion,
    LocalDateTime ultimoAcceso,
    
    // Información adicional
    String nombreCompleto,
    Boolean tieneInformacionFinanciera
) {
}
