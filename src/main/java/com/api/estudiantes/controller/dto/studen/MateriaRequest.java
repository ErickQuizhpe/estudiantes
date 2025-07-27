package com.api.estudiantes.controller.dto.studen;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MateriaRequest(
    @NotBlank(message = "El código es obligatorio")
    String codigo,
    
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,
    
    String descripcion,
    
    @NotNull(message = "Los créditos son obligatorios")
    @Positive(message = "Los créditos deben ser positivos")
    Integer creditos,
    
    @NotNull(message = "El semestre es obligatorio")
    @Positive(message = "El semestre debe ser positivo")
    Integer semestre,
    
    @NotBlank(message = "La carrera es obligatoria")
    String carrera,
    
    Boolean activa
) {
}
