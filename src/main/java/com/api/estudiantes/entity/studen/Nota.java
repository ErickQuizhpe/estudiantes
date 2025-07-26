package com.api.estudiantes.entity.studen;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "notas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nota {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Double calificacion;
    
    @Column(name = "nota_maxima", nullable = false)
    private Double notaMaxima;
    
    @Column(name = "porcentaje")
    private Double porcentaje;
    
    @Column(name = "tipo_evaluacion", nullable = false)
    private String tipoEvaluacion; // "Parcial", "Final", "Tarea", "Quiz", etc.
    
    @Column(name = "fecha_evaluacion")
    private LocalDate fechaEvaluacion;
    
    @Column(name = "observaciones")
    private String observaciones;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoNota estado;
    
    // Relaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Studen estudiante;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;
    
    public enum EstadoNota {
        ACTIVA, ANULADA, PENDIENTE
    }
    
    // Método para calcular si está aprobado
    public Boolean isAprobado() {
        return calificacion >= (notaMaxima * 0.7); // 70% para aprobar
    }
}
