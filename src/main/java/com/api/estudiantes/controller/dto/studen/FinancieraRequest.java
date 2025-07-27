package com.api.estudiantes.controller.dto.studen;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FinancieraRequest(
    @NotNull(message = "La pensión mensual es obligatoria")
    @Positive(message = "La pensión mensual debe ser positiva")
    BigDecimal pensionMensual,
    
    @DecimalMin(value = "0.0", message = "El descuento no puede ser negativo")
    BigDecimal descuento,
    
    @DecimalMin(value = "0.0", message = "El monto pendiente no puede ser negativo")
    BigDecimal montoPendiente,
    
    LocalDate ultimoPago,
    
    LocalDate fechaVencimiento,
    
    Boolean alDia,
    
    Boolean becado,
    
    @Min(value = 0, message = "El porcentaje de beca no puede ser negativo")
    @Max(value = 100, message = "El porcentaje de beca no puede ser mayor a 100")
    Integer porcentajeBeca,
    
    String observaciones,
    
    @NotNull(message = "El ID del estudiante es obligatorio")
    Long estudianteId
) {
}
