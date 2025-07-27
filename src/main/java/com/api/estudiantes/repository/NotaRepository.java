package com.api.estudiantes.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.estudiantes.entity.studen.Nota;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Long> {
  
  List<Nota> findByActivaTrue();
  
  List<Nota> findByEstudianteId(Long estudianteId);
  
  List<Nota> findByEstudianteIdAndActivaTrue(Long estudianteId);
  
  List<Nota> findByMateriaId(Long materiaId);
  
  List<Nota> findByMateriaIdAndActivaTrue(Long materiaId);
  
  List<Nota> findByEstudianteIdAndMateriaId(Long estudianteId, Long materiaId);
  
  List<Nota> findByEstudianteIdAndMateriaIdAndActivaTrue(Long estudianteId, Long materiaId);
  
  List<Nota> findByTipoEvaluacion(String tipoEvaluacion);
  
  List<Nota> findByFechaEvaluacionBetween(LocalDate fechaInicio, LocalDate fechaFin);
  
  @Query("SELECT n FROM Nota n WHERE n.estudiante.id = :estudianteId AND n.calificacion >= (n.notaMaxima * 0.7)")
  List<Nota> findNotasAprobadasByEstudiante(@Param("estudianteId") Long estudianteId);
  
  @Query("SELECT n FROM Nota n WHERE n.estudiante.id = :estudianteId AND n.calificacion < (n.notaMaxima * 0.7)")
  List<Nota> findNotasReprobadasByEstudiante(@Param("estudianteId") Long estudianteId);
  
  @Query("SELECT AVG(n.calificacion) FROM Nota n WHERE n.estudiante.id = :estudianteId AND n.activa = true")
  Double findPromedioByEstudiante(@Param("estudianteId") Long estudianteId);
  
  @Query("SELECT AVG(n.calificacion) FROM Nota n WHERE n.materia.id = :materiaId AND n.activa = true")
  Double findPromedioByMateria(@Param("materiaId") Long materiaId);
  
  @Query("SELECT n FROM Nota n WHERE n.estudiante.id = :estudianteId AND n.materia.id = :materiaId ORDER BY n.fechaEvaluacion DESC")
  List<Nota> findByEstudianteAndMateriaOrderByFechaDesc(@Param("estudianteId") Long estudianteId, @Param("materiaId") Long materiaId);
}
