package com.api.estudiantes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.estudiantes.entity.user.RoleEntity;
import com.api.estudiantes.entity.user.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  
  Optional<RoleEntity> findByName(RoleName name);
}
