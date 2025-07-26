package com.api.estudiantes.controller.dto.auth;

import com.api.estudiantes.controller.dto.user.UserResponse;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "user", "token" })
public record LoginResponse(UserResponse user, String token) {

}
