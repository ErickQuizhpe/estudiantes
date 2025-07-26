package com.api.estudiantes.entity.user;

import java.time.LocalDateTime;
import java.util.Set;

import com.api.estudiantes.entity.studen.Studen;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  @Builder.Default
  private boolean enabled = true;

  @Column(name = "fecha_creacion", nullable = false, updatable = false)
  private LocalDateTime fechaCreacion;

  @Column(name = "fecha_actualizacion")
  private LocalDateTime fechaActualizacion;

  @Column(name = "ultimo_acceso")
  private LocalDateTime ultimoAcceso;

  @Column(name = "avatar_url")
  private String avatarUrl;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  @Builder.Default
  private Set<RoleEntity> roles = java.util.Collections.emptySet();

  // Relación con estudiante (solo si el usuario tiene rol ESTUDIANTE)
  @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonManagedReference("user-student")
  private Studen estudiante;

  @PrePersist
  protected void onCreate() {
    fechaCreacion = LocalDateTime.now();
    fechaActualizacion = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    fechaActualizacion = LocalDateTime.now();
  }

  // Métodos de utilidad

  public void actualizarUltimoAcceso() {
    this.ultimoAcceso = LocalDateTime.now();
  }

  public String getNombreCompleto() {
    return firstName + " " + lastName;
  }

  public boolean esEstudiante() {
    return roles.stream()
        .anyMatch(role -> role.getName() == RoleName.ESTUDIANTE);
  }

  public boolean esAdmin() {
    return roles.stream()
        .anyMatch(role -> role.getName() == RoleName.ADMIN);
  }

}
