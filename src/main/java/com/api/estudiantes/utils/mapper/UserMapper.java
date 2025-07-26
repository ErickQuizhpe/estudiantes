package com.api.estudiantes.utils.mapper;

import org.mapstruct.Mapper;
import com.api.estudiantes.controller.dto.user.UserResponse;
import com.api.estudiantes.entity.user.RoleEntity;
import com.api.estudiantes.entity.user.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

  public abstract UserResponse toUserResponse(UserEntity userEntity);

  default String map(RoleEntity roleEntity) {
    return roleEntity.getName().name();
  }

}
