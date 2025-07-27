package com.api.estudiantes.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.estudiantes.controller.dto.company.BannerRequest;
import com.api.estudiantes.controller.dto.company.BannerResponse;
import com.api.estudiantes.controller.dto.company.CompanyRequest;
import com.api.estudiantes.controller.dto.company.CompanyResponse;
import com.api.estudiantes.controller.dto.company.SocialMediaRequest;
import com.api.estudiantes.controller.dto.company.SocialMediaResponse;
import com.api.estudiantes.entity.company.Banner;
import com.api.estudiantes.entity.company.Company;
import com.api.estudiantes.entity.company.SocialMedia;
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
    
    // Actualizar campos básicos
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
    
    // Manejar Social Media
    if (company.getSocialMedia() != null) {
      // Limpiar las redes sociales existentes
      existingCompany.getSocialMedia().clear();
      // Agregar las nuevas redes sociales
      existingCompany.getSocialMedia().addAll(company.getSocialMedia());
    }
    
    // Manejar Banners
    if (company.getBanners() != null) {
      // Limpiar los banners existentes
      existingCompany.getBanners().clear();
      // Agregar los nuevos banners
      existingCompany.getBanners().addAll(company.getBanners());
    }

    return companyRepository.save(existingCompany);
  }

  // Métodos con DTOs
  public CompanyResponse updateCompanyWithDto(Long id, CompanyRequest request) {
    Company existingCompany = companyRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
    
    // Actualizar campos básicos
    existingCompany.setName(request.getName());
    existingCompany.setDescription(request.getDescription());
    existingCompany.setLogoUrl(request.getLogoUrl());
    existingCompany.setPhone(request.getPhone());
    existingCompany.setEmail(request.getEmail());
    existingCompany.setAddress(request.getAddress());
    existingCompany.setCity(request.getCity());
    existingCompany.setFooterText(request.getFooterText());
    existingCompany.setMission(request.getMission());
    existingCompany.setVision(request.getVision());
    
    // Manejar Social Media
    if (request.getSocialMedia() != null) {
      existingCompany.getSocialMedia().clear();
      for (SocialMediaRequest socialMediaReq : request.getSocialMedia()) {
        SocialMedia socialMedia = SocialMedia.builder()
            .platform(socialMediaReq.getPlatform())
            .url(socialMediaReq.getUrl())
            .iconUrl(socialMediaReq.getIconUrl())
            .active(socialMediaReq.getActive())
            .build();
        existingCompany.getSocialMedia().add(socialMedia);
      }
    }
    
    // Manejar Banners
    if (request.getBanners() != null) {
      existingCompany.getBanners().clear();
      for (BannerRequest bannerReq : request.getBanners()) {
        Banner banner = Banner.builder()
            .description(bannerReq.getDescription())
            .imageUrl(bannerReq.getImageUrl())
            .active(bannerReq.getActive())
            .build();
        existingCompany.getBanners().add(banner);
      }
    }

    Company savedCompany = companyRepository.save(existingCompany);
    return mapToCompanyResponse(savedCompany);
  }

  public CompanyResponse getCompanyDto() {
    Company company = getCompany();
    return mapToCompanyResponse(company);
  }

  private CompanyResponse mapToCompanyResponse(Company company) {
    return CompanyResponse.builder()
        .id(company.getId())
        .name(company.getName())
        .description(company.getDescription())
        .logoUrl(company.getLogoUrl())
        .phone(company.getPhone())
        .email(company.getEmail())
        .address(company.getAddress())
        .city(company.getCity())
        .footerText(company.getFooterText())
        .mission(company.getMission())
        .vision(company.getVision())
        .socialMedia(company.getSocialMedia().stream()
            .map(this::mapToSocialMediaResponse)
            .collect(Collectors.toSet()))
        .banners(company.getBanners().stream()
            .map(this::mapToBannerResponse)
            .collect(Collectors.toList()))
        .build();
  }

  private SocialMediaResponse mapToSocialMediaResponse(SocialMedia socialMedia) {
    return SocialMediaResponse.builder()
        .id(socialMedia.getId())
        .platform(socialMedia.getPlatform())
        .url(socialMedia.getUrl())
        .iconUrl(socialMedia.getIconUrl())
        .active(socialMedia.getActive())
        .build();
  }

  private BannerResponse mapToBannerResponse(Banner banner) {
    return BannerResponse.builder()
        .id(banner.getId())
        .description(banner.getDescription())
        .imageUrl(banner.getImageUrl())
        .active(banner.getActive())
        .build();
  }
}
