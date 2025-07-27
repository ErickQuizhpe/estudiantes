package com.api.estudiantes.entity.studen;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "financiera")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Financiera {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "pension_mensual", nullable = false, precision = 10, scale = 2)
    private BigDecimal pensionMensual;
    
    @Column(name = "descuento", precision = 10, scale = 2)
    private BigDecimal descuento;
    
    @Column(name = "monto_pendiente", precision = 10, scale = 2)
    private BigDecimal montoPendiente;
    
    @Column(name = "ultimo_pago")
    private LocalDate ultimoPago;
    
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
    
    @Column(name = "al_dia", nullable = false)
    @Builder.Default
    private Boolean alDia = true;
    
    @Column(name = "becado", nullable = false)
    @Builder.Default
    private Boolean becado = false;
    
    @Column(name = "porcentaje_beca")
    private Integer porcentajeBeca;
    
    @Column(name = "observaciones")
    private String observaciones;
    
    // Relación con estudiante
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Studen estudiante;
    
    // Método para calcular monto a pagar
    public BigDecimal calcularMontoPagar() {
        BigDecimal montoBase = pensionMensual;
        
        if (becado && porcentajeBeca != null && porcentajeBeca > 0) {
            BigDecimal descuentoBeca = montoBase.multiply(BigDecimal.valueOf(porcentajeBeca))
                    .divide(BigDecimal.valueOf(100));
            montoBase = montoBase.subtract(descuentoBeca);
        }
        
        if (descuento != null) {
            montoBase = montoBase.subtract(descuento);
        }
        
        return montoBase;
    }
}
