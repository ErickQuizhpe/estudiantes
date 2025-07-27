package com.api.estudiantes.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.estudiantes.entity.studen.Financiera;

@Repository
public interface FinancieraRepository extends JpaRepository<Financiera, Long> {
  
  Optional<Financiera> findByEstudianteId(Long estudianteId);
  
  List<Financiera> findByAlDiaTrue();
  
  List<Financiera> findByAlDiaFalse();
  
  List<Financiera> findByBecadoTrue();
  
  List<Financiera> findByBecadoFalse();
  
  List<Financiera> findByBecadoTrueAndPorcentajeBecaGreaterThan(Integer porcentaje);
  
  List<Financiera> findByFechaVencimientoBefore(LocalDate fecha);
  
  List<Financiera> findByFechaVencimientoBetween(LocalDate fechaInicio, LocalDate fechaFin);
  
  @Query("SELECT f FROM Financiera f WHERE f.montoPendiente > :monto")
  List<Financiera> findByMontoPendienteGreaterThan(@Param("monto") BigDecimal monto);
  
  @Query("SELECT f FROM Financiera f WHERE f.pensionMensual BETWEEN :min AND :max")
  List<Financiera> findByPensionMensualBetween(@Param("min") BigDecimal min, @Param("max") BigDecimal max);
  
  @Query("SELECT SUM(f.montoPendiente) FROM Financiera f WHERE f.alDia = false")
  BigDecimal sumMontoPendienteEnMora();
  
  @Query("SELECT COUNT(f) FROM Financiera f WHERE f.alDia = false")
  Long countEstudiantesEnMora();
  
  @Query("SELECT COUNT(f) FROM Financiera f WHERE f.becado = true")
  Long countEstudiantesBecados();
  
  @Query("SELECT f FROM Financiera f WHERE f.ultimoPago IS NULL OR f.ultimoPago < :fecha")
  List<Financiera> findBySinPagosDespuesDe(@Param("fecha") LocalDate fecha);
}
