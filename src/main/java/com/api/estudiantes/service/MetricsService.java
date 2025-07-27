package com.api.estudiantes.service;

import org.springframework.stereotype.Service;

import com.api.estudiantes.repository.FinancieraRepository;
import com.api.estudiantes.repository.MateriaRepository;
import com.api.estudiantes.repository.NotaRepository;
import com.api.estudiantes.repository.StudenRepository;
import com.api.estudiantes.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetricsService {

    private final StudenRepository studenRepository;
    private final MateriaRepository materiaRepository;
    private final NotaRepository notaRepository;
    private final UserRepository userRepository;
    private final FinancieraRepository financieraRepository;

    /**
     * Obtiene el número total de estudiantes
     */
    public long getTotalStudentsCount() {
        try {
            return studenRepository.count();
        } catch (Exception e) {
            log.error("Error al obtener el conteo de estudiantes: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Obtiene el número total de materias
     */
    public long getTotalSubjectsCount() {
        try {
            return materiaRepository.count();
        } catch (Exception e) {
            log.error("Error al obtener el conteo de materias: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Obtiene el número total de notas/calificaciones
     */
    public long getTotalGradesCount() {
        try {
            return notaRepository.count();
        } catch (Exception e) {
            log.error("Error al obtener el conteo de notas: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Obtiene el número de usuarios activos
     */
    public long getActiveUsersCount() {
        try {
            return userRepository.countByActivoTrue();
        } catch (Exception e) {
            log.error("Error al obtener el conteo de usuarios activos: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Obtiene el número de estudiantes activos
     */
    public long getActiveStudentsCount() {
        try {
            return studenRepository.findByActivoTrue().size();
        } catch (Exception e) {
            log.error("Error al obtener el conteo de estudiantes activos: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Obtiene el número de materias activas
     */
    public long getActiveSubjectsCount() {
        try {
            return materiaRepository.findByActivaTrue().size();
        } catch (Exception e) {
            log.error("Error al obtener el conteo de materias activas: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Obtiene el número total de registros financieros
     */
    public long getTotalFinancialRecordsCount() {
        try {
            return financieraRepository.count();
        } catch (Exception e) {
            log.error("Error al obtener el conteo de registros financieros: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Verifica si la base de datos está saludable
     */
    public boolean isDatabaseHealthy() {
        try {
            // Intenta hacer una consulta simple
            studenRepository.count();
            return true;
        } catch (Exception e) {
            log.error("Error de conectividad con la base de datos: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene métricas agregadas del sistema
     */
    public SystemMetrics getSystemMetrics() {
        return SystemMetrics.builder()
                .totalStudents(getTotalStudentsCount())
                .activeStudents(getActiveStudentsCount())
                .totalSubjects(getTotalSubjectsCount())
                .activeSubjects(getActiveSubjectsCount())
                .totalGrades(getTotalGradesCount())
                .activeUsers(getActiveUsersCount())
                .totalFinancialRecords(getTotalFinancialRecordsCount())
                .databaseHealthy(isDatabaseHealthy())
                .build();
    }

    /**
     * Clase para encapsular métricas del sistema
     */
    @lombok.Builder
    @lombok.Data
    public static class SystemMetrics {
        private long totalStudents;
        private long activeStudents;
        private long totalSubjects;
        private long activeSubjects;
        private long totalGrades;
        private long activeUsers;
        private long totalFinancialRecords;
        private boolean databaseHealthy;
    }
}
