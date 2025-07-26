package com.api.estudiantes.controller.dto.user;

import java.util.Set;

public record UserRequest(
    String username,
    String email,
    String password,
    String firstName,
    String lastName,
    boolean enabled,
    Set<String> roles) {
}
