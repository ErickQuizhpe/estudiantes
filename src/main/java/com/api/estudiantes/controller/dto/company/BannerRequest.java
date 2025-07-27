package com.api.estudiantes.controller.dto.company;

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
public class BannerRequest {

    private Long id;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    private String description;

    @Size(max = 300, message = "La URL de la imagen no puede tener más de 300 caracteres")
    private String imageUrl;

    private Boolean active;
}
