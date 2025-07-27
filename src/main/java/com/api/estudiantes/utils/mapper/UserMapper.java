package com.api.estudiantes.utils.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;

import com.api.estudiantes.controller.dto.user.UserResponse;
import com.api.estudiantes.entity.user.RoleEntity;
import com.api.estudiantes.entity.user.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserResponse toUserResponse(UserEntity userEntity);

  default Set<String> map(Set<RoleEntity> roles) {
    if (roles == null) {
      return null;
    }
    return roles.stream()
        .map(role -> role.getName().name())
        .collect(Collectors.toSet());
  }

}
