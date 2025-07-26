package com.api.estudiantes.controller.dto.user;

import java.util.Set;

public record UserResponse(
		Long id,
		String username,
		String email,
		String firstName,
		String lastName,
		boolean enabled,
		Set<String> roles) {

}
