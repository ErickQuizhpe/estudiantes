package com.api.estudiantes.controller.dto.studen;

public record MateriaResponse(
    Long id,
    String codigo,
    String nombre,
    String descripcion,
    Integer creditos,
    Integer semestre,
    String carrera,
    Boolean activa,
    Integer totalNotas
) {
}
