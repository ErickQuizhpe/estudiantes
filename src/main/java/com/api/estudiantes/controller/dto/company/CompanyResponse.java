package com.api.estudiantes.controller.dto.company;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {

    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String footerText;
    private String mission;
    private String vision;
    private Set<SocialMediaResponse> socialMedia;
    private List<BannerResponse> banners;
}
