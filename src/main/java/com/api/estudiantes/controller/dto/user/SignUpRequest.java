package com.api.estudiantes.controller.dto.user;

public record SignUpRequest(
    String firstName,
    String lastName,
    String username,
    String email,
    String password) {

}
