package com.api.estudiantes.controller.dto.company;

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
public class SocialMediaRequest {

    private Long id;

    @NotBlank(message = "La plataforma es obligatoria")
    @Size(max = 50, message = "La plataforma no puede tener más de 50 caracteres")
    private String platform;

    @NotBlank(message = "La URL es obligatoria")
    @Size(max = 200, message = "La URL no puede tener más de 200 caracteres")
    private String url;

    @Size(max = 200, message = "La URL del icono no puede tener más de 200 caracteres")
    private String iconUrl;

    private Boolean active;
}
