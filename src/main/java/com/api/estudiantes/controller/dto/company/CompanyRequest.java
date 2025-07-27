package com.api.estudiantes.controller.dto.company;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class CompanyRequest {

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String name;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    private String description;

    private String logoUrl;

    @Size(max = 15, message = "El teléfono no puede tener más de 15 caracteres")
    private String phone;

    @Email(message = "Debe ser un email válido")
    private String email;

    @Size(max = 200, message = "La dirección no puede tener más de 200 caracteres")
    private String address;

    @Size(max = 50, message = "La ciudad no puede tener más de 50 caracteres")
    private String city;

    @Size(max = 200, message = "El texto del footer no puede tener más de 200 caracteres")
    private String footerText;

    @Size(max = 1000, message = "La misión no puede tener más de 1000 caracteres")
    private String mission;

    @Size(max = 1000, message = "La visión no puede tener más de 1000 caracteres")
    private String vision;

    private Set<SocialMediaRequest> socialMedia;
    private List<BannerRequest> banners;
}
