package com.api.estudiantes.entity.studen;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.api.estudiantes.entity.user.UserEntity;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "estudiantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Studen {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String matricula;
    
    @Column(nullable = false)
    private String telefono;
    
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    @Column(nullable = false)
    private String carrera;
    
    @Column(nullable = false)
    private Integer semestre;
    
    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;
    
    // Relación con usuario
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonBackReference("user-student")
    private UserEntity usuario;
    
    // Relación con notas
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Nota> notas;
    
    // Relación con información financiera
    @OneToOne(mappedBy = "estudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Financiera informacionFinanciera;
    
    // Métodos de utilidad para acceder a datos del usuario
    public String getNombres() {
        return usuario != null ? usuario.getFirstName() : null;
    }
    
    public String getApellidos() {
        return usuario != null ? usuario.getLastName() : null;
    }
    
    public String getEmail() {
        return usuario != null ? usuario.getEmail() : null;
    }
    
    public String getNombreCompleto() {
        return usuario != null ? usuario.getNombreCompleto() : null;
    }
}
