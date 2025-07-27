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

import com.api.estudiantes.controller.dto.studen.StudenRequest;
import com.api.estudiantes.controller.dto.studen.StudenResponse;
import com.api.estudiantes.service.StudenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/estudiantes")
@Tag(name = "Estudiantes", description = "Gestión de estudiantes")
@SecurityRequirement(name = "bearerAuth")
public class StudenController {

    private final StudenService studenService;

    @PostMapping
    @Operation(summary = "Crear un nuevo estudiante", description = "Crea un estudiante con su usuario asociado")
    public ResponseEntity<StudenResponse> createStuden(@Valid @RequestBody StudenRequest request) {
        return ResponseEntity.ok(studenService.createStuden(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener estudiante por ID", description = "Obtiene un estudiante específico por su ID")
    public ResponseEntity<StudenResponse> getStudenById(@PathVariable Long id) {
        return ResponseEntity.ok(studenService.getStudenById(id));
    }

    @GetMapping("/matricula/{matricula}")
    @Operation(summary = "Obtener estudiante por matrícula", description = "Obtiene un estudiante por su matrícula")
    public ResponseEntity<StudenResponse> getStudenByMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(studenService.getStudenByMatricula(matricula));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener estudiante por ID de usuario", description = "Obtiene un estudiante por su ID de usuario")
    public ResponseEntity<StudenResponse> getStudenByUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(studenService.getStudenByUsuarioId(usuarioId));
    }

    @GetMapping
    @Operation(summary = "Obtener todos los estudiantes", description = "Lista todos los estudiantes")
    public ResponseEntity<List<StudenResponse>> getAllStudens() {
        return ResponseEntity.ok(studenService.getAllStudens());
    }

    @GetMapping("/carrera/{carrera}")
    @Operation(summary = "Obtener estudiantes por carrera", description = "Lista estudiantes de una carrera específica")
    public ResponseEntity<List<StudenResponse>> getStudensByCarrera(@PathVariable String carrera) {
        return ResponseEntity.ok(studenService.getStudensByCarrera(carrera));
    }

    @GetMapping("/carrera/{carrera}/semestre/{semestre}")
    @Operation(summary = "Obtener estudiantes por carrera y semestre", description = "Lista estudiantes de una carrera y semestre específicos")
    public ResponseEntity<List<StudenResponse>> getStudensByCarreraAndSemestre(
            @PathVariable String carrera,
            @PathVariable Integer semestre) {
        return ResponseEntity.ok(studenService.getStudensByCarreraAndSemestre(carrera, semestre));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar estudiantes", description = "Busca estudiantes por nombre o matrícula")
    public ResponseEntity<List<StudenResponse>> searchStudens(@RequestParam String nombre) {
        return ResponseEntity.ok(studenService.searchStudens(nombre));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estudiante", description = "Actualiza la información de un estudiante")
    public ResponseEntity<StudenResponse> updateStuden(
            @PathVariable Long id,
            @Valid @RequestBody StudenRequest request) {
        return ResponseEntity.ok(studenService.updateStuden(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar estudiante", description = "Elimina un estudiante y su usuario asociado")
    public ResponseEntity<Void> deleteStuden(@PathVariable Long id) {
        studenService.deleteStuden(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activar")
    @Operation(summary = "Activar estudiante", description = "Activa un estudiante y su usuario")
    public ResponseEntity<StudenResponse> activateStuden(@PathVariable Long id) {
        return ResponseEntity.ok(studenService.activateStuden(id));
    }

    @PutMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar estudiante", description = "Desactiva un estudiante y su usuario")
    public ResponseEntity<StudenResponse> deactivateStuden(@PathVariable Long id) {
        return ResponseEntity.ok(studenService.deactivateStuden(id));
    }
}
