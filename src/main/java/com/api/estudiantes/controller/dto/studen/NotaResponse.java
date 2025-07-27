package com.api.estudiantes.controller.dto.studen;

import java.time.LocalDate;

public record NotaResponse(
    Long id,
    Double calificacion,
    Double notaMaxima,
    Double porcentaje,
    String tipoEvaluacion,
    LocalDate fechaEvaluacion,
    String observaciones,
    Boolean activa,
    Boolean aprobado,
    
    // Información del estudiante
    Long estudianteId,
    String estudianteMatricula,
    String estudianteNombre,
    
    // Información de la materia
    Long materiaId,
    String materiaCodigo,
    String materiaNombre
) {
}
