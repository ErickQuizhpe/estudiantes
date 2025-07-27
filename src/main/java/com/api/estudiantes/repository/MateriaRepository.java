package com.api.estudiantes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.estudiantes.entity.studen.Materia;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
  
  Optional<Materia> findByCodigo(String codigo);
  
  boolean existsByCodigo(String codigo);
  
  List<Materia> findByActivaTrue();
  
  List<Materia> findByCarrera(String carrera);
  
  List<Materia> findByCarreraAndActivaTrue(String carrera);
  
  List<Materia> findBySemestre(Integer semestre);
  
  List<Materia> findByCarreraAndSemestre(String carrera, Integer semestre);
  
  List<Materia> findByCarreraAndSemestreAndActivaTrue(String carrera, Integer semestre);
  
  @Query("SELECT m FROM Materia m WHERE " +
         "LOWER(m.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) OR " +
         "LOWER(m.codigo) LIKE LOWER(CONCAT('%', :nombre, '%'))")
  List<Materia> findByNombreOrCodigoContaining(@Param("nombre") String nombre);
  
  @Query("SELECT m FROM Materia m WHERE m.creditos BETWEEN :min AND :max")
  List<Materia> findByCreditosBetween(@Param("min") Integer min, @Param("max") Integer max);
}
