package com.api.estudiantes.exception;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Schema(description = "Error response for API requests")
public class ApiErrorResponse {
  private LocalDateTime timestamp;
  private int status;
  private String error;
  private List<String> details;
}
