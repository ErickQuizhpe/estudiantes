package com.api.estudiantes.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.estudiantes.controller.dto.studen.MateriaRequest;
import com.api.estudiantes.controller.dto.studen.MateriaResponse;
import com.api.estudiantes.entity.studen.Materia;
import com.api.estudiantes.repository.MateriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MateriaService {

    private final MateriaRepository materiaRepository;

    public MateriaResponse createMateria(MateriaRequest request) {
        // Verificar que el código no exista
        if (materiaRepository.existsByCodigo(request.codigo())) {
            throw new RuntimeException("El código de materia ya existe");
        }

        Materia materia = Materia.builder()
                .codigo(request.codigo())
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .creditos(request.creditos())
                .semestre(request.semestre())
                .carrera(request.carrera())
                .activa(request.activa() != null ? request.activa() : true)
                .build();

        Materia savedMateria = materiaRepository.save(materia);
        return mapToMateriaResponse(savedMateria);
    }

    public MateriaResponse getMateriaById(Long id) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
        return mapToMateriaResponse(materia);
    }

    public MateriaResponse getMateriaByCodigo(String codigo) {
        Materia materia = materiaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
        return mapToMateriaResponse(materia);
    }

    public List<MateriaResponse> getAllMaterias() {
        return materiaRepository.findAll().stream()
                .map(this::mapToMateriaResponse)
                .collect(Collectors.toList());
    }

    public List<MateriaResponse> getMateriasActivas() {
        return materiaRepository.findByActivaTrue().stream()
                .map(this::mapToMateriaResponse)
                .collect(Collectors.toList());
    }

    public List<MateriaResponse> getMateriasByCarrera(String carrera) {
        return materiaRepository.findByCarreraAndActivaTrue(carrera).stream()
                .map(this::mapToMateriaResponse)
                .collect(Collectors.toList());
    }

    public List<MateriaResponse> getMateriasBySemestre(Integer semestre) {
        return materiaRepository.findBySemestre(semestre).stream()
                .map(this::mapToMateriaResponse)
                .collect(Collectors.toList());
    }

    public List<MateriaResponse> getMateriasByCarreraAndSemestre(String carrera, Integer semestre) {
        return materiaRepository.findByCarreraAndSemestreAndActivaTrue(carrera, semestre).stream()
                .map(this::mapToMateriaResponse)
                .collect(Collectors.toList());
    }

    public List<MateriaResponse> searchMaterias(String nombre) {
        return materiaRepository.findByNombreOrCodigoContaining(nombre).stream()
                .map(this::mapToMateriaResponse)
                .collect(Collectors.toList());
    }

    public List<MateriaResponse> getMateriasByCreditos(Integer min, Integer max) {
        return materiaRepository.findByCreditosBetween(min, max).stream()
                .map(this::mapToMateriaResponse)
                .collect(Collectors.toList());
    }

    public MateriaResponse updateMateria(Long id, MateriaRequest request) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        // Verificar duplicados si se cambia el código
        if (!materia.getCodigo().equals(request.codigo()) && 
            materiaRepository.existsByCodigo(request.codigo())) {
            throw new RuntimeException("El código de materia ya existe");
        }

        // Actualizar campos
        materia.setCodigo(request.codigo());
        materia.setNombre(request.nombre());
        materia.setDescripcion(request.descripcion());
        materia.setCreditos(request.creditos());
        materia.setSemestre(request.semestre());
        materia.setCarrera(request.carrera());
        if (request.activa() != null) {
            materia.setActiva(request.activa());
        }

        Materia updatedMateria = materiaRepository.save(materia);
        return mapToMateriaResponse(updatedMateria);
    }

    public void deleteMateria(Long id) {
        if (!materiaRepository.existsById(id)) {
            throw new RuntimeException("Materia no encontrada");
        }
        materiaRepository.deleteById(id);
    }

    public MateriaResponse activateMateria(Long id) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
        
        materia.setActiva(true);
        Materia updatedMateria = materiaRepository.save(materia);
        return mapToMateriaResponse(updatedMateria);
    }

    public MateriaResponse deactivateMateria(Long id) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
        
        materia.setActiva(false);
        Materia updatedMateria = materiaRepository.save(materia);
        return mapToMateriaResponse(updatedMateria);
    }

    private MateriaResponse mapToMateriaResponse(Materia materia) {
        return new MateriaResponse(
            materia.getId(),
            materia.getCodigo(),
            materia.getNombre(),
            materia.getDescripcion(),
            materia.getCreditos(),
            materia.getSemestre(),
            materia.getCarrera(),
            materia.getActiva(),
            materia.getNotas() != null ? materia.getNotas().size() : 0
        );
    }
}
