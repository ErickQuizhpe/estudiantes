package com.api.estudiantes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.estudiantes.controller.dto.company.CompanyRequest;
import com.api.estudiantes.controller.dto.company.CompanyResponse;
import com.api.estudiantes.entity.company.Company;
import com.api.estudiantes.service.CompanyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
@Tag(name = "Company", description = "Gestión de información de la empresa/institución")
public class CompanyController {

  private final CompanyService service;

  @SecurityRequirement(name = "bearerAuth")
  @PostMapping()
  @Operation(summary = "Crear nueva empresa", description = "Crea una nueva empresa/institución")
  public ResponseEntity<Company> createCompany(@RequestBody Company company) {
    return ResponseEntity.ok(service.createCompany(company));
  }

  @GetMapping()
  @Operation(summary = "Obtener información de la empresa", description = "Obtiene la información de la empresa/institución")
  public ResponseEntity<CompanyResponse> getCompany() {
    return ResponseEntity.ok(service.getCompanyDto());
  }

  @PutMapping("/{id}")
  @SecurityRequirement(name = "bearerAuth")
  @Operation(summary = "Actualizar empresa", description = "Actualiza la información de la empresa/institución")
  public ResponseEntity<CompanyResponse> updateCompany(@PathVariable Long id, @Valid @RequestBody CompanyRequest request) {
    return ResponseEntity.ok(service.updateCompanyWithDto(id, request));
  }
}
