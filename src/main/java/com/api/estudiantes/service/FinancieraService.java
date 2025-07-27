package com.api.estudiantes.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.estudiantes.controller.dto.studen.FinancieraRequest;
import com.api.estudiantes.controller.dto.studen.FinancieraResponse;
import com.api.estudiantes.entity.studen.Financiera;
import com.api.estudiantes.entity.studen.Studen;
import com.api.estudiantes.repository.FinancieraRepository;
import com.api.estudiantes.repository.StudenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FinancieraService {

    private final FinancieraRepository financieraRepository;
    private final StudenRepository studenRepository;

    public FinancieraResponse createFinanciera(FinancieraRequest request) {
        // Verificar que el estudiante exista
        Studen estudiante = studenRepository.findById(request.estudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Verificar que el estudiante no tenga ya información financiera
        if (financieraRepository.findByEstudianteId(request.estudianteId()).isPresent()) {
            throw new RuntimeException("El estudiante ya tiene información financiera registrada");
        }

        Financiera financiera = Financiera.builder()
                .pensionMensual(request.pensionMensual())
                .descuento(request.descuento())
                .montoPendiente(request.montoPendiente() != null ? request.montoPendiente() : BigDecimal.ZERO)
                .ultimoPago(request.ultimoPago())
                .fechaVencimiento(request.fechaVencimiento())
                .alDia(request.alDia() != null ? request.alDia() : true)
                .becado(request.becado() != null ? request.becado() : false)
                .porcentajeBeca(request.porcentajeBeca())
                .observaciones(request.observaciones())
                .estudiante(estudiante)
                .build();

        Financiera savedFinanciera = financieraRepository.save(financiera);
        return mapToFinancieraResponse(savedFinanciera);
    }

    public FinancieraResponse getFinancieraById(Long id) {
        Financiera financiera = financieraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Información financiera no encontrada"));
        return mapToFinancieraResponse(financiera);
    }

    public FinancieraResponse getFinancieraByEstudiante(Long estudianteId) {
        Financiera financiera = financieraRepository.findByEstudianteId(estudianteId)
                .orElseThrow(() -> new RuntimeException("Información financiera no encontrada para el estudiante"));
        return mapToFinancieraResponse(financiera);
    }

    public List<FinancieraResponse> getAllFinancieras() {
        return financieraRepository.findAll().stream()
                .map(this::mapToFinancieraResponse)
                .collect(Collectors.toList());
    }

    public List<FinancieraResponse> getEstudiantesAlDia() {
        return financieraRepository.findByAlDiaTrue().stream()
                .map(this::mapToFinancieraResponse)
                .collect(Collectors.toList());
    }

    public List<FinancieraResponse> getEstudiantesEnMora() {
        return financieraRepository.findByAlDiaFalse().stream()
                .map(this::mapToFinancieraResponse)
                .collect(Collectors.toList());
    }

    public List<FinancieraResponse> getEstudiantesBecados() {
        return financieraRepository.findByBecadoTrue().stream()
                .map(this::mapToFinancieraResponse)
                .collect(Collectors.toList());
    }

    public List<FinancieraResponse> getEstudiantesBecadosConPorcentajeMayorA(Integer porcentaje) {
        return financieraRepository.findByBecadoTrueAndPorcentajeBecaGreaterThan(porcentaje).stream()
                .map(this::mapToFinancieraResponse)
                .collect(Collectors.toList());
    }

    public List<FinancieraResponse> getEstudiantesConVencimientoAntesDe(LocalDate fecha) {
        return financieraRepository.findByFechaVencimientoBefore(fecha).stream()
                .map(this::mapToFinancieraResponse)
                .collect(Collectors.toList());
    }

    public List<FinancieraResponse> getEstudiantesConVencimientoEntre(LocalDate fechaInicio, LocalDate fechaFin) {
        return financieraRepository.findByFechaVencimientoBetween(fechaInicio, fechaFin).stream()
                .map(this::mapToFinancieraResponse)
                .collect(Collectors.toList());
    }

    public List<FinancieraResponse> getEstudiantesConMontoPendienteMayorA(BigDecimal monto) {
        return financieraRepository.findByMontoPendienteGreaterThan(monto).stream()
                .map(this::mapToFinancieraResponse)
                .collect(Collectors.toList());
    }

    public List<FinancieraResponse> getEstudiantesConPensionEntre(BigDecimal min, BigDecimal max) {
        return financieraRepository.findByPensionMensualBetween(min, max).stream()
                .map(this::mapToFinancieraResponse)
                .collect(Collectors.toList());
    }

    public List<FinancieraResponse> getEstudiantesSinPagosDespuesDe(LocalDate fecha) {
        return financieraRepository.findBySinPagosDespuesDe(fecha).stream()
                .map(this::mapToFinancieraResponse)
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalMontoPendienteEnMora() {
        BigDecimal total = financieraRepository.sumMontoPendienteEnMora();
        return total != null ? total : BigDecimal.ZERO;
    }

    public Long getCountEstudiantesEnMora() {
        return financieraRepository.countEstudiantesEnMora();
    }

    public Long getCountEstudiantesBecados() {
        return financieraRepository.countEstudiantesBecados();
    }

    public FinancieraResponse updateFinanciera(Long id, FinancieraRequest request) {
        Financiera financiera = financieraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Información financiera no encontrada"));

        // Verificar que el estudiante exista si se está cambiando
        if (!financiera.getEstudiante().getId().equals(request.estudianteId())) {
            Studen estudiante = studenRepository.findById(request.estudianteId())
                    .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
            financiera.setEstudiante(estudiante);
        }

        // Actualizar campos
        financiera.setPensionMensual(request.pensionMensual());
        financiera.setDescuento(request.descuento());
        financiera.setMontoPendiente(request.montoPendiente());
        financiera.setUltimoPago(request.ultimoPago());
        financiera.setFechaVencimiento(request.fechaVencimiento());
        if (request.alDia() != null) {
            financiera.setAlDia(request.alDia());
        }
        if (request.becado() != null) {
            financiera.setBecado(request.becado());
        }
        financiera.setPorcentajeBeca(request.porcentajeBeca());
        financiera.setObservaciones(request.observaciones());

        Financiera updatedFinanciera = financieraRepository.save(financiera);
        return mapToFinancieraResponse(updatedFinanciera);
    }

    public FinancieraResponse registrarPago(Long id, BigDecimal montoPago, LocalDate fechaPago) {
        Financiera financiera = financieraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Información financiera no encontrada"));

        // Actualizar último pago
        financiera.setUltimoPago(fechaPago);
        
        // Reducir monto pendiente
        if (financiera.getMontoPendiente() != null) {
            BigDecimal nuevoMontoPendiente = financiera.getMontoPendiente().subtract(montoPago);
            financiera.setMontoPendiente(nuevoMontoPendiente.max(BigDecimal.ZERO));
        }

        // Actualizar estado al día si el monto pendiente es 0
        if (financiera.getMontoPendiente() != null && 
            financiera.getMontoPendiente().compareTo(BigDecimal.ZERO) <= 0) {
            financiera.setAlDia(true);
        }

        Financiera updatedFinanciera = financieraRepository.save(financiera);
        return mapToFinancieraResponse(updatedFinanciera);
    }

    public void deleteFinanciera(Long id) {
        if (!financieraRepository.existsById(id)) {
            throw new RuntimeException("Información financiera no encontrada");
        }
        financieraRepository.deleteById(id);
    }

    private FinancieraResponse mapToFinancieraResponse(Financiera financiera) {
        // Calcular días de vencimiento
        Integer diasVencimiento = null;
        if (financiera.getFechaVencimiento() != null) {
            LocalDate hoy = LocalDate.now();
            if (financiera.getFechaVencimiento().isBefore(hoy)) {
                diasVencimiento = (int) ChronoUnit.DAYS.between(financiera.getFechaVencimiento(), hoy);
            } else {
                diasVencimiento = (int) ChronoUnit.DAYS.between(hoy, financiera.getFechaVencimiento()) * -1;
            }
        }

        // Calcular total descuento
        BigDecimal totalDescuento = BigDecimal.ZERO;
        if (financiera.getDescuento() != null) {
            totalDescuento = totalDescuento.add(financiera.getDescuento());
        }
        if (financiera.getBecado() && financiera.getPorcentajeBeca() != null && financiera.getPorcentajeBeca() > 0) {
            BigDecimal descuentoBeca = financiera.getPensionMensual()
                    .multiply(BigDecimal.valueOf(financiera.getPorcentajeBeca()))
                    .divide(BigDecimal.valueOf(100));
            totalDescuento = totalDescuento.add(descuentoBeca);
        }

        return new FinancieraResponse(
            financiera.getId(),
            financiera.getPensionMensual(),
            financiera.getDescuento(),
            financiera.getMontoPendiente(),
            financiera.getUltimoPago(),
            financiera.getFechaVencimiento(),
            financiera.getAlDia(),
            financiera.getBecado(),
            financiera.getPorcentajeBeca(),
            financiera.getObservaciones(),
            financiera.getEstudiante().getId(),
            financiera.getEstudiante().getMatricula(),
            financiera.getEstudiante().getNombreCompleto(),
            financiera.calcularMontoPagar(),
            totalDescuento,
            diasVencimiento
        );
    }
}
