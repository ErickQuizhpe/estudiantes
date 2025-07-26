package com.api.estudiantes.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.estudiantes.entity.company.Company;
import com.api.estudiantes.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {

  private final CompanyRepository companyRepository;

  public Company createCompany(Company company) {
    return companyRepository.save(company);
  }

  public Company getCompany() {
    List<Company> companies = companyRepository.findAll();
    if (companies.isEmpty()) {
      throw new RuntimeException("No se encontró ninguna empresa");
    }
    // Retorna la primera empresa (asumiendo que solo hay una)
    return companies.get(0);
  }

  public Company updateCompany(Long id, Company company) {
    Company existingCompany = companyRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
    
    // Actualizar campos
    existingCompany.setName(company.getName());
    existingCompany.setDescription(company.getDescription());
    existingCompany.setLogoUrl(company.getLogoUrl());
    existingCompany.setPhone(company.getPhone());
    existingCompany.setEmail(company.getEmail());
    existingCompany.setAddress(company.getAddress());
    existingCompany.setCity(company.getCity());
    existingCompany.setFooterText(company.getFooterText());
    existingCompany.setMission(company.getMission());
    existingCompany.setVision(company.getVision());
    
    if (company.getBanners() != null) {
      existingCompany.setBanners(company.getBanners());
    }
    
    if (company.getSocialMedia() != null) {
      existingCompany.setSocialMedia(company.getSocialMedia());
    }

    return companyRepository.save(existingCompany);
  }
}
