package com.api.estudiantes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.estudiantes.entity.studen.Studen;

@Repository
public interface StudenRepository extends JpaRepository<Studen, Long> {
  
  Optional<Studen> findByMatricula(String matricula);
  
  boolean existsByMatricula(String matricula);
  
  List<Studen> findByActivoTrue();
  
  List<Studen> findByActivoFalse();
  
  List<Studen> findByCarrera(String carrera);
  
  List<Studen> findByCarreraAndActivoTrue(String carrera);
  
  List<Studen> findBySemestre(Integer semestre);
  
  List<Studen> findByCarreraAndSemestre(String carrera, Integer semestre);
  
  @Query("SELECT s FROM Studen s WHERE s.usuario.id = :usuarioId")
  Optional<Studen> findByUsuarioId(@Param("usuarioId") Long usuarioId);
  
  @Query("SELECT s FROM Studen s WHERE s.usuario.email = :email")
  Optional<Studen> findByUsuarioEmail(@Param("email") String email);
  
  @Query("SELECT s FROM Studen s WHERE s.usuario.username = :username")
  Optional<Studen> findByUsuarioUsername(@Param("username") String username);
  
  @Query("SELECT s FROM Studen s WHERE " +
         "LOWER(s.usuario.firstName) LIKE LOWER(CONCAT('%', :nombre, '%')) OR " +
         "LOWER(s.usuario.lastName) LIKE LOWER(CONCAT('%', :nombre, '%')) OR " +
         "LOWER(s.matricula) LIKE LOWER(CONCAT('%', :nombre, '%'))")
  List<Studen> findByNombreOrMatriculaContaining(@Param("nombre") String nombre);
}
