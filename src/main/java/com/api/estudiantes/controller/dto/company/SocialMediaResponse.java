package com.api.estudiantes.controller.dto.company;

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
public class SocialMediaResponse {

    private Long id;
    private String platform;
    private String url;
    private String iconUrl;
    private Boolean active;
}
