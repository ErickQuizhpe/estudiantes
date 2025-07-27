package com.api.estudiantes.entity.studen;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "materias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Materia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String codigo;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(nullable = false)
    private Integer creditos;
    
    @Column(nullable = false)
    private Integer semestre;
    
    @Column(nullable = false)
    private String carrera;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean activa = true;
    
    // Relación con notas
    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL)
    private List<Nota> notas;
}
