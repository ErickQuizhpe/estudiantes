package com.api.estudiantes.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.estudiantes.controller.dto.studen.NotaRequest;
import com.api.estudiantes.controller.dto.studen.NotaResponse;
import com.api.estudiantes.entity.studen.Materia;
import com.api.estudiantes.entity.studen.Nota;
import com.api.estudiantes.entity.studen.Studen;
import com.api.estudiantes.repository.MateriaRepository;
import com.api.estudiantes.repository.NotaRepository;
import com.api.estudiantes.repository.StudenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotaService {

    private final NotaRepository notaRepository;
    private final StudenRepository studenRepository;
    private final MateriaRepository materiaRepository;

    public NotaResponse createNota(NotaRequest request) {
        // Verificar que el estudiante exista
        Studen estudiante = studenRepository.findById(request.estudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Verificar que la materia exista
        Materia materia = materiaRepository.findById(request.materiaId())
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        Nota nota = Nota.builder()
                .calificacion(request.calificacion())
                .notaMaxima(request.notaMaxima())
                .porcentaje(request.porcentaje())
                .tipoEvaluacion(request.tipoEvaluacion())
                .fechaEvaluacion(request.fechaEvaluacion() != null ? request.fechaEvaluacion() : LocalDate.now())
                .observaciones(request.observaciones())
                .activa(request.activa() != null ? request.activa() : true)
                .estudiante(estudiante)
                .materia(materia)
                .build();

        Nota savedNota = notaRepository.save(nota);
        return mapToNotaResponse(savedNota);
    }

    public NotaResponse getNotaById(Long id) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada"));
        return mapToNotaResponse(nota);
    }

    public List<NotaResponse> getAllNotas() {
        return notaRepository.findAll().stream()
                .map(this::mapToNotaResponse)
                .collect(Collectors.toList());
    }

    public List<NotaResponse> getNotasActivas() {
        return notaRepository.findByActivaTrue().stream()
                .map(this::mapToNotaResponse)
                .collect(Collectors.toList());
    }

    public List<NotaResponse> getNotasByEstudiante(Long estudianteId) {
        return notaRepository.findByEstudianteIdAndActivaTrue(estudianteId).stream()
                .map(this::mapToNotaResponse)
                .collect(Collectors.toList());
    }

    public List<NotaResponse> getNotasByMateria(Long materiaId) {
        return notaRepository.findByMateriaIdAndActivaTrue(materiaId).stream()
                .map(this::mapToNotaResponse)
                .collect(Collectors.toList());
    }

    public List<NotaResponse> getNotasByEstudianteAndMateria(Long estudianteId, Long materiaId) {
        return notaRepository.findByEstudianteIdAndMateriaIdAndActivaTrue(estudianteId, materiaId).stream()
                .map(this::mapToNotaResponse)
                .collect(Collectors.toList());
    }

    public List<NotaResponse> getNotasByTipoEvaluacion(String tipoEvaluacion) {
        return notaRepository.findByTipoEvaluacion(tipoEvaluacion).stream()
                .map(this::mapToNotaResponse)
                .collect(Collectors.toList());
    }

    public List<NotaResponse> getNotasByFechaRange(LocalDate fechaInicio, LocalDate fechaFin) {
        return notaRepository.findByFechaEvaluacionBetween(fechaInicio, fechaFin).stream()
                .map(this::mapToNotaResponse)
                .collect(Collectors.toList());
    }

    public List<NotaResponse> getNotasAprobadasByEstudiante(Long estudianteId) {
        return notaRepository.findNotasAprobadasByEstudiante(estudianteId).stream()
                .map(this::mapToNotaResponse)
                .collect(Collectors.toList());
    }

    public List<NotaResponse> getNotasReprobadasByEstudiante(Long estudianteId) {
        return notaRepository.findNotasReprobadasByEstudiante(estudianteId).stream()
                .map(this::mapToNotaResponse)
                .collect(Collectors.toList());
    }

    public Double getPromedioByEstudiante(Long estudianteId) {
        Double promedio = notaRepository.findPromedioByEstudiante(estudianteId);
        return promedio != null ? promedio : 0.0;
    }

    public Double getPromedioByMateria(Long materiaId) {
        Double promedio = notaRepository.findPromedioByMateria(materiaId);
        return promedio != null ? promedio : 0.0;
    }

    public List<NotaResponse> getHistorialNotasByEstudianteAndMateria(Long estudianteId, Long materiaId) {
        return notaRepository.findByEstudianteAndMateriaOrderByFechaDesc(estudianteId, materiaId).stream()
                .map(this::mapToNotaResponse)
                .collect(Collectors.toList());
    }

    public NotaResponse updateNota(Long id, NotaRequest request) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada"));

        // Verificar que el estudiante exista si se está cambiando
        if (!nota.getEstudiante().getId().equals(request.estudianteId())) {
            Studen estudiante = studenRepository.findById(request.estudianteId())
                    .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
            nota.setEstudiante(estudiante);
        }

        // Verificar que la materia exista si se está cambiando
        if (!nota.getMateria().getId().equals(request.materiaId())) {
            Materia materia = materiaRepository.findById(request.materiaId())
                    .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
            nota.setMateria(materia);
        }

        // Actualizar campos
        nota.setCalificacion(request.calificacion());
        nota.setNotaMaxima(request.notaMaxima());
        nota.setPorcentaje(request.porcentaje());
        nota.setTipoEvaluacion(request.tipoEvaluacion());
        nota.setFechaEvaluacion(request.fechaEvaluacion());
        nota.setObservaciones(request.observaciones());
        if (request.activa() != null) {
            nota.setActiva(request.activa());
        }

        Nota updatedNota = notaRepository.save(nota);
        return mapToNotaResponse(updatedNota);
    }

    public void deleteNota(Long id) {
        if (!notaRepository.existsById(id)) {
            throw new RuntimeException("Nota no encontrada");
        }
        notaRepository.deleteById(id);
    }

    public NotaResponse activateNota(Long id) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada"));
        
        nota.setActiva(true);
        Nota updatedNota = notaRepository.save(nota);
        return mapToNotaResponse(updatedNota);
    }

    public NotaResponse deactivateNota(Long id) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada"));
        
        nota.setActiva(false);
        Nota updatedNota = notaRepository.save(nota);
        return mapToNotaResponse(updatedNota);
    }

    private NotaResponse mapToNotaResponse(Nota nota) {
        return new NotaResponse(
            nota.getId(),
            nota.getCalificacion(),
            nota.getNotaMaxima(),
            nota.getPorcentaje(),
            nota.getTipoEvaluacion(),
            nota.getFechaEvaluacion(),
            nota.getObservaciones(),
            nota.getActiva(),
            nota.isAprobado(),
            nota.getEstudiante().getId(),
            nota.getEstudiante().getMatricula(),
            nota.getEstudiante().getNombreCompleto(),
            nota.getMateria().getId(),
            nota.getMateria().getCodigo(),
            nota.getMateria().getNombre()
        );
    }
}
