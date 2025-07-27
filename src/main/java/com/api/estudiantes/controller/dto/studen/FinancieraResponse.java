package com.api.estudiantes.controller.dto.studen;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FinancieraResponse(
    Long id,
    BigDecimal pensionMensual,
    BigDecimal descuento,
    BigDecimal montoPendiente,
    LocalDate ultimoPago,
    LocalDate fechaVencimiento,
    Boolean alDia,
    Boolean becado,
    Integer porcentajeBeca,
    String observaciones,
    
    // Información del estudiante
    Long estudianteId,
    String estudianteMatricula,
    String estudianteNombre,
    
    // Cálculos
    BigDecimal montoAPagar,
    BigDecimal totalDescuento,
    Integer diasVencimiento
) {
}
