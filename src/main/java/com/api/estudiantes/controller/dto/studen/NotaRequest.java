package com.api.estudiantes.controller.dto.studen;

import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NotaRequest(
    @NotNull(message = "La calificación es obligatoria")
    @DecimalMin(value = "0.0", message = "La calificación no puede ser negativa")
    Double calificacion,
    
    @NotNull(message = "La nota máxima es obligatoria")
    @Positive(message = "La nota máxima debe ser positiva")
    Double notaMaxima,
    
    @DecimalMin(value = "0.0", message = "El porcentaje no puede ser negativo")
    @DecimalMax(value = "100.0", message = "El porcentaje no puede ser mayor a 100")
    Double porcentaje,
    
    @NotBlank(message = "El tipo de evaluación es obligatorio")
    String tipoEvaluacion,
    
    LocalDate fechaEvaluacion,
    
    String observaciones,
    
    Boolean activa,
    
    @NotNull(message = "El ID del estudiante es obligatorio")
    Long estudianteId,
    
    @NotNull(message = "El ID de la materia es obligatorio")
    Long materiaId
) {
}
