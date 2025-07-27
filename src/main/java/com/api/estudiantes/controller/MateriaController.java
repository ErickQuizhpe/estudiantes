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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.estudiantes.controller.dto.studen.MateriaRequest;
import com.api.estudiantes.controller.dto.studen.MateriaResponse;
import com.api.estudiantes.service.MateriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/materias")
@Tag(name = "Materias", description = "Gestión de materias académicas")
@SecurityRequirement(name = "bearerAuth")
public class MateriaController {

    private final MateriaService materiaService;

    @PostMapping
    @Operation(summary = "Crear una nueva materia", description = "Registra una nueva materia en el sistema")
    public ResponseEntity<MateriaResponse> createMateria(@Valid @RequestBody MateriaRequest request) {
        return ResponseEntity.ok(materiaService.createMateria(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener materia por ID", description = "Obtiene una materia específica por su ID")
    public ResponseEntity<MateriaResponse> getMateriaById(@PathVariable Long id) {
        return ResponseEntity.ok(materiaService.getMateriaById(id));
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Obtener materia por código", description = "Obtiene una materia por su código único")
    public ResponseEntity<MateriaResponse> getMateriaByCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(materiaService.getMateriaByCodigo(codigo));
    }

    @GetMapping
    @Operation(summary = "Obtener todas las materias", description = "Lista todas las materias registradas")
    public ResponseEntity<List<MateriaResponse>> getAllMaterias(
            @RequestParam(defaultValue = "false") boolean soloActivas) {
        if (soloActivas) {
            return ResponseEntity.ok(materiaService.getMateriasActivas());
        }
        return ResponseEntity.ok(materiaService.getAllMaterias());
    }

    @GetMapping("/carrera/{carrera}")
    @Operation(summary = "Obtener materias por carrera", description = "Lista materias de una carrera específica")
    public ResponseEntity<List<MateriaResponse>> getMateriasByCarrera(@PathVariable String carrera) {
        return ResponseEntity.ok(materiaService.getMateriasByCarrera(carrera));
    }

    @GetMapping("/semestre/{semestre}")
    @Operation(summary = "Obtener materias por semestre", description = "Lista materias de un semestre específico")
    public ResponseEntity<List<MateriaResponse>> getMateriasBySemestre(@PathVariable Integer semestre) {
        return ResponseEntity.ok(materiaService.getMateriasBySemestre(semestre));
    }

    @GetMapping("/carrera/{carrera}/semestre/{semestre}")
    @Operation(summary = "Obtener materias por carrera y semestre", description = "Lista materias de una carrera y semestre específicos")
    public ResponseEntity<List<MateriaResponse>> getMateriasByCarreraAndSemestre(
            @PathVariable String carrera,
            @PathVariable Integer semestre) {
        return ResponseEntity.ok(materiaService.getMateriasByCarreraAndSemestre(carrera, semestre));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar materias", description = "Busca materias por nombre o código")
    public ResponseEntity<List<MateriaResponse>> searchMaterias(@RequestParam String nombre) {
        return ResponseEntity.ok(materiaService.searchMaterias(nombre));
    }

    @GetMapping("/creditos")
    @Operation(summary = "Obtener materias por rango de créditos", description = "Lista materias dentro de un rango de créditos")
    public ResponseEntity<List<MateriaResponse>> getMateriasByCreditos(
            @RequestParam Integer min,
            @RequestParam Integer max) {
        return ResponseEntity.ok(materiaService.getMateriasByCreditos(min, max));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar materia", description = "Actualiza la información de una materia")
    public ResponseEntity<MateriaResponse> updateMateria(
            @PathVariable Long id,
            @Valid @RequestBody MateriaRequest request) {
        return ResponseEntity.ok(materiaService.updateMateria(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar materia", description = "Elimina una materia del sistema")
    public ResponseEntity<Void> deleteMateria(@PathVariable Long id) {
        materiaService.deleteMateria(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activar")
    @Operation(summary = "Activar materia", description = "Activa una materia")
    public ResponseEntity<MateriaResponse> activateMateria(@PathVariable Long id) {
        return ResponseEntity.ok(materiaService.activateMateria(id));
    }

    @PutMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar materia", description = "Desactiva una materia")
    public ResponseEntity<MateriaResponse> deactivateMateria(@PathVariable Long id) {
        return ResponseEntity.ok(materiaService.deactivateMateria(id));
    }
}
