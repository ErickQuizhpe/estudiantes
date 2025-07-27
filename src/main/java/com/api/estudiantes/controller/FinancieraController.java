package com.api.estudiantes.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.estudiantes.controller.dto.studen.FinancieraRequest;
import com.api.estudiantes.controller.dto.studen.FinancieraResponse;
import com.api.estudiantes.service.FinancieraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/financiera")
@Tag(name = "Información Financiera", description = "Gestión de información financiera de estudiantes")
@SecurityRequirement(name = "bearerAuth")
public class FinancieraController {

    private final FinancieraService financieraService;

    @PostMapping
    @Operation(summary = "Crear información financiera", description = "Registra información financiera para un estudiante")
    public ResponseEntity<FinancieraResponse> createFinanciera(@Valid @RequestBody FinancieraRequest request) {
        return ResponseEntity.ok(financieraService.createFinanciera(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener información financiera por ID", description = "Obtiene información financiera específica por su ID")
    public ResponseEntity<FinancieraResponse> getFinancieraById(@PathVariable Long id) {
        return ResponseEntity.ok(financieraService.getFinancieraById(id));
    }

    @GetMapping("/estudiante/{estudianteId}")
    @Operation(summary = "Obtener información financiera por estudiante", description = "Obtiene la información financiera de un estudiante")
    public ResponseEntity<FinancieraResponse> getFinancieraByEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(financieraService.getFinancieraByEstudiante(estudianteId));
    }

    @GetMapping
    @Operation(summary = "Obtener toda la información financiera", description = "Lista toda la información financiera registrada")
    public ResponseEntity<List<FinancieraResponse>> getAllFinancieras() {
        return ResponseEntity.ok(financieraService.getAllFinancieras());
    }

    @GetMapping("/al-dia")
    @Operation(summary = "Obtener estudiantes al día", description = "Lista estudiantes que están al día con sus pagos")
    public ResponseEntity<List<FinancieraResponse>> getEstudiantesAlDia() {
        return ResponseEntity.ok(financieraService.getEstudiantesAlDia());
    }

    @GetMapping("/en-mora")
    @Operation(summary = "Obtener estudiantes en mora", description = "Lista estudiantes que están en mora")
    public ResponseEntity<List<FinancieraResponse>> getEstudiantesEnMora() {
        return ResponseEntity.ok(financieraService.getEstudiantesEnMora());
    }

    @GetMapping("/becados")
    @Operation(summary = "Obtener estudiantes becados", description = "Lista estudiantes que tienen beca")
    public ResponseEntity<List<FinancieraResponse>> getEstudiantesBecados() {
        return ResponseEntity.ok(financieraService.getEstudiantesBecados());
    }

    @GetMapping("/becados/porcentaje/{porcentaje}")
    @Operation(summary = "Obtener estudiantes becados con porcentaje mayor a", description = "Lista estudiantes con beca mayor al porcentaje especificado")
    public ResponseEntity<List<FinancieraResponse>> getEstudiantesBecadosConPorcentajeMayorA(@PathVariable Integer porcentaje) {
        return ResponseEntity.ok(financieraService.getEstudiantesBecadosConPorcentajeMayorA(porcentaje));
    }

    @GetMapping("/vencimiento/antes")
    @Operation(summary = "Obtener estudiantes con vencimiento antes de fecha", description = "Lista estudiantes con fecha de vencimiento anterior a la especificada")
    public ResponseEntity<List<FinancieraResponse>> getEstudiantesConVencimientoAntesDe(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(financieraService.getEstudiantesConVencimientoAntesDe(fecha));
    }

    @GetMapping("/vencimiento/entre")
    @Operation(summary = "Obtener estudiantes con vencimiento entre fechas", description = "Lista estudiantes con fecha de vencimiento en el rango especificado")
    public ResponseEntity<List<FinancieraResponse>> getEstudiantesConVencimientoEntre(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(financieraService.getEstudiantesConVencimientoEntre(fechaInicio, fechaFin));
    }

    @GetMapping("/monto-pendiente")
    @Operation(summary = "Obtener estudiantes con monto pendiente mayor a", description = "Lista estudiantes con monto pendiente mayor al especificado")
    public ResponseEntity<List<FinancieraResponse>> getEstudiantesConMontoPendienteMayorA(@RequestParam BigDecimal monto) {
        return ResponseEntity.ok(financieraService.getEstudiantesConMontoPendienteMayorA(monto));
    }

    @GetMapping("/pension/rango")
    @Operation(summary = "Obtener estudiantes por rango de pensión", description = "Lista estudiantes con pensión en el rango especificado")
    public ResponseEntity<List<FinancieraResponse>> getEstudiantesConPensionEntre(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        return ResponseEntity.ok(financieraService.getEstudiantesConPensionEntre(min, max));
    }

    @GetMapping("/sin-pagos-despues")
    @Operation(summary = "Obtener estudiantes sin pagos después de fecha", description = "Lista estudiantes que no han pagado después de la fecha especificada")
    public ResponseEntity<List<FinancieraResponse>> getEstudiantesSinPagosDespuesDe(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(financieraService.getEstudiantesSinPagosDespuesDe(fecha));
    }

    @GetMapping("/estadisticas/monto-pendiente-total")
    @Operation(summary = "Obtener monto total pendiente en mora", description = "Calcula el monto total pendiente de estudiantes en mora")
    public ResponseEntity<BigDecimal> getTotalMontoPendienteEnMora() {
        return ResponseEntity.ok(financieraService.getTotalMontoPendienteEnMora());
    }

    @GetMapping("/estadisticas/count-en-mora")
    @Operation(summary = "Contar estudiantes en mora", description = "Cuenta el número de estudiantes en mora")
    public ResponseEntity<Long> getCountEstudiantesEnMora() {
        return ResponseEntity.ok(financieraService.getCountEstudiantesEnMora());
    }

    @GetMapping("/estadisticas/count-becados")
    @Operation(summary = "Contar estudiantes becados", description = "Cuenta el número de estudiantes becados")
    public ResponseEntity<Long> getCountEstudiantesBecados() {
        return ResponseEntity.ok(financieraService.getCountEstudiantesBecados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar información financiera", description = "Actualiza la información financiera de un estudiante")
    public ResponseEntity<FinancieraResponse> updateFinanciera(
            @PathVariable Long id,
            @Valid @RequestBody FinancieraRequest request) {
        return ResponseEntity.ok(financieraService.updateFinanciera(id, request));
    }

    @PutMapping("/{id}/registrar-pago")
    @Operation(summary = "Registrar pago", description = "Registra un pago realizado por el estudiante")
    public ResponseEntity<FinancieraResponse> registrarPago(
            @PathVariable Long id,
            @RequestParam BigDecimal montoPago,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaPago) {
        return ResponseEntity.ok(financieraService.registrarPago(id, montoPago, fechaPago));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar información financiera", description = "Elimina la información financiera de un estudiante")
    public ResponseEntity<Void> deleteFinanciera(@PathVariable Long id) {
        financieraService.deleteFinanciera(id);
        return ResponseEntity.noContent().build();
    }
}
