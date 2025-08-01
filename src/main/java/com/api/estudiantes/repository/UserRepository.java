package com.api.estudiantes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.estudiantes.entity.user.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  
  Optional<UserEntity> findByUsername(String username);
  
  Optional<UserEntity> findByEmail(String email);
  
  boolean existsByUsername(String username);
  
  boolean existsByEmail(String email);
  
  // Método para métricas
  long countByActivoTrue();
}
