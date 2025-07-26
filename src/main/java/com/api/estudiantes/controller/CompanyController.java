package com.api.estudiantes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.estudiantes.entity.company.Company;
import com.api.estudiantes.service.CompanyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
@Tag(name = "Company", description = "Company endpoints")
public class CompanyController {

  private final CompanyService service;

  @SecurityRequirement(name = "bearerAuth")
  @PostMapping()
  @Operation(summary = "Create a new company")
  public ResponseEntity<Company> createCompany(@RequestBody Company company) {
    return ResponseEntity.ok(service.createCompany(company));
  }

  @GetMapping()
  @Operation(summary = "Get a company")
  public ResponseEntity<Company> getCompany() {
    return ResponseEntity.ok(service.getCompany());
  }

  @PutMapping("/{id}")
  @SecurityRequirement(name = "bearerAuth")
  @Operation(summary = "Update a company")
  public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company company) {
    return ResponseEntity.ok(service.updateCompany(id, company));
  }
}
