package com.api.estudiantes.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.estudiantes.service.MetricsService;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/metrics")
@Tag(name = "Monitoreo", description = "Endpoints de monitoreo y métricas personalizadas")
public class MonitoringController implements HealthIndicator {

    private final MeterRegistry meterRegistry;
    private final MetricsService metricsService;

    @GetMapping("/custom")
    @Operation(summary = "Métricas personalizadas", description = "Obtiene métricas personalizadas del sistema")
    public ResponseEntity<Map<String, Object>> getCustomMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        // Métricas básicas
        metrics.put("timestamp", LocalDateTime.now());
        metrics.put("application", "Sistema de Gestión de Estudiantes");
        
        // Métricas de la aplicación
        metrics.put("total_requests", getCounterValue("http_requests_total"));
        metrics.put("active_users", metricsService.getActiveUsersCount());
        metrics.put("total_students", metricsService.getTotalStudentsCount());
        metrics.put("total_subjects", metricsService.getTotalSubjectsCount());
        metrics.put("total_grades", metricsService.getTotalGradesCount());
        
        // Métricas de sistema
        Runtime runtime = Runtime.getRuntime();
        metrics.put("memory_used_mb", (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024);
        metrics.put("memory_free_mb", runtime.freeMemory() / 1024 / 1024);
        metrics.put("memory_total_mb", runtime.totalMemory() / 1024 / 1024);
        metrics.put("processors", runtime.availableProcessors());
        
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/requests")
    @Operation(summary = "Métricas de peticiones", description = "Obtiene métricas detalladas de peticiones HTTP")
    public ResponseEntity<Map<String, Object>> getRequestMetrics() {
        Map<String, Object> requestMetrics = new HashMap<>();
        
        // Incrementar contador de consultas de métricas
        Counter.builder("metrics_requests")
                .description("Número de consultas a métricas")
                .register(meterRegistry)
                .increment();
        
        requestMetrics.put("timestamp", LocalDateTime.now());
        requestMetrics.put("total_http_requests", getCounterValue("http.server.requests"));
        requestMetrics.put("metrics_queries", getCounterValue("metrics_requests"));
        
        // Tiempo de respuesta promedio (si existe)
        Timer httpTimer = meterRegistry.find("http.server.requests").timer();
        if (httpTimer != null) {
            requestMetrics.put("average_response_time_ms", httpTimer.mean(java.util.concurrent.TimeUnit.MILLISECONDS));
            requestMetrics.put("max_response_time_ms", httpTimer.max(java.util.concurrent.TimeUnit.MILLISECONDS));
            requestMetrics.put("total_time_ms", httpTimer.totalTime(java.util.concurrent.TimeUnit.MILLISECONDS));
        }
        
        return ResponseEntity.ok(requestMetrics);
    }

    @GetMapping("/health-custom")
    @Operation(summary = "Estado personalizado", description = "Estado personalizado de la aplicación")
    public ResponseEntity<Map<String, Object>> getCustomHealth() {
        Map<String, Object> healthInfo = new HashMap<>();
        
        healthInfo.put("status", "UP");
        healthInfo.put("timestamp", LocalDateTime.now());
        healthInfo.put("database_connection", metricsService.isDatabaseHealthy() ? "UP" : "DOWN");
        healthInfo.put("api_version", "1.0.0");
        healthInfo.put("environment", getActiveProfile());
        
        return ResponseEntity.ok(healthInfo);
    }

    @Override
    public Health health() {
        if (metricsService.isDatabaseHealthy()) {
            return Health.up()
                    .withDetail("database", "Available")
                    .withDetail("students_count", metricsService.getTotalStudentsCount())
                    .withDetail("timestamp", LocalDateTime.now())
                    .build();
        } else {
            return Health.down()
                    .withDetail("database", "Unavailable")
                    .withDetail("timestamp", LocalDateTime.now())
                    .build();
        }
    }

    private double getCounterValue(String counterName) {
        Counter counter = meterRegistry.find(counterName).counter();
        return counter != null ? counter.count() : 0.0;
    }

    private String getActiveProfile() {
        return System.getProperty("spring.profiles.active", "default");
    }
}
