package com.api.estudiantes.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.estudiantes.controller.dto.studen.NotaRequest;
import com.api.estudiantes.controller.dto.studen.NotaResponse;
import com.api.estudiantes.service.NotaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notas")
@Tag(name = "Notas", description = "Gestión de calificaciones y evaluaciones")
@SecurityRequirement(name = "bearerAuth")
public class NotaController {

    private final NotaService notaService;

    @PostMapping
    @Operation(summary = "Registrar una nueva nota", description = "Registra una calificación para un estudiante en una materia")
    public ResponseEntity<NotaResponse> createNota(@Valid @RequestBody NotaRequest request) {
        return ResponseEntity.ok(notaService.createNota(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener nota por ID", description = "Obtiene una nota específica por su ID")
    public ResponseEntity<NotaResponse> getNotaById(@PathVariable Long id) {
        return ResponseEntity.ok(notaService.getNotaById(id));
    }

    @GetMapping
    @Operation(summary = "Obtener todas las notas", description = "Lista todas las notas registradas")
    public ResponseEntity<List<NotaResponse>> getAllNotas(
            @RequestParam(defaultValue = "false") boolean soloActivas) {
        if (soloActivas) {
            return ResponseEntity.ok(notaService.getNotasActivas());
        }
        return ResponseEntity.ok(notaService.getAllNotas());
    }

    @GetMapping("/estudiante/{estudianteId}")
    @Operation(summary = "Obtener notas por estudiante", description = "Lista todas las notas de un estudiante")
    public ResponseEntity<List<NotaResponse>> getNotasByEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(notaService.getNotasByEstudiante(estudianteId));
    }

    @GetMapping("/materia/{materiaId}")
    @Operation(summary = "Obtener notas por materia", description = "Lista todas las notas de una materia")
    public ResponseEntity<List<NotaResponse>> getNotasByMateria(@PathVariable Long materiaId) {
        return ResponseEntity.ok(notaService.getNotasByMateria(materiaId));
    }

    @GetMapping("/estudiante/{estudianteId}/materia/{materiaId}")
    @Operation(summary = "Obtener notas por estudiante y materia", description = "Lista notas de un estudiante en una materia específica")
    public ResponseEntity<List<NotaResponse>> getNotasByEstudianteAndMateria(
            @PathVariable Long estudianteId,
            @PathVariable Long materiaId) {
        return ResponseEntity.ok(notaService.getNotasByEstudianteAndMateria(estudianteId, materiaId));
    }

    @GetMapping("/estudiante/{estudianteId}/materia/{materiaId}/historial")
    @Operation(summary = "Obtener historial de notas", description = "Obtiene el historial completo de notas de un estudiante en una materia")
    public ResponseEntity<List<NotaResponse>> getHistorialNotasByEstudianteAndMateria(
            @PathVariable Long estudianteId,
            @PathVariable Long materiaId) {
        return ResponseEntity.ok(notaService.getHistorialNotasByEstudianteAndMateria(estudianteId, materiaId));
    }

    @GetMapping("/tipo/{tipoEvaluacion}")
    @Operation(summary = "Obtener notas por tipo de evaluación", description = "Lista notas por tipo de evaluación (Parcial, Final, etc.)")
    public ResponseEntity<List<NotaResponse>> getNotasByTipoEvaluacion(@PathVariable String tipoEvaluacion) {
        return ResponseEntity.ok(notaService.getNotasByTipoEvaluacion(tipoEvaluacion));
    }

    @GetMapping("/fecha")
    @Operation(summary = "Obtener notas por rango de fechas", description = "Lista notas dentro de un rango de fechas")
    public ResponseEntity<List<NotaResponse>> getNotasByFechaRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(notaService.getNotasByFechaRange(fechaInicio, fechaFin));
    }

    @GetMapping("/estudiante/{estudianteId}/aprobadas")
    @Operation(summary = "Obtener notas aprobadas", description = "Lista notas aprobadas de un estudiante")
    public ResponseEntity<List<NotaResponse>> getNotasAprobadasByEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(notaService.getNotasAprobadasByEstudiante(estudianteId));
    }

    @GetMapping("/estudiante/{estudianteId}/reprobadas")
    @Operation(summary = "Obtener notas reprobadas", description = "Lista notas reprobadas de un estudiante")
    public ResponseEntity<List<NotaResponse>> getNotasReprobadasByEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(notaService.getNotasReprobadasByEstudiante(estudianteId));
    }

    @GetMapping("/estudiante/{estudianteId}/promedio")
    @Operation(summary = "Obtener promedio del estudiante", description = "Calcula el promedio general de un estudiante")
    public ResponseEntity<Double> getPromedioByEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(notaService.getPromedioByEstudiante(estudianteId));
    }

    @GetMapping("/materia/{materiaId}/promedio")
    @Operation(summary = "Obtener promedio de la materia", description = "Calcula el promedio general de una materia")
    public ResponseEntity<Double> getPromedioByMateria(@PathVariable Long materiaId) {
        return ResponseEntity.ok(notaService.getPromedioByMateria(materiaId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar nota", description = "Actualiza una calificación existente")
    public ResponseEntity<NotaResponse> updateNota(
            @PathVariable Long id,
            @Valid @RequestBody NotaRequest request) {
        return ResponseEntity.ok(notaService.updateNota(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar nota", description = "Elimina una nota del sistema")
    public ResponseEntity<Void> deleteNota(@PathVariable Long id) {
        notaService.deleteNota(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activar")
    @Operation(summary = "Activar nota", description = "Activa una nota")
    public ResponseEntity<NotaResponse> activateNota(@PathVariable Long id) {
        return ResponseEntity.ok(notaService.activateNota(id));
    }

    @PutMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar nota", description = "Desactiva una nota (anular)")
    public ResponseEntity<NotaResponse> deactivateNota(@PathVariable Long id) {
        return ResponseEntity.ok(notaService.deactivateNota(id));
    }
}
