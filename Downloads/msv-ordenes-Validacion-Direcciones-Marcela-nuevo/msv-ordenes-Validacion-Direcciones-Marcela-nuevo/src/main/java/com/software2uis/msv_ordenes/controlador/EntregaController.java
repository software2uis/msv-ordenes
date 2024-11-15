package com.software2uis.msv_ordenes.controlador;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software2uis.msv_ordenes.modelo.Entrega;
import com.software2uis.msv_ordenes.modelo.Pedido;
import com.software2uis.msv_ordenes.repositorio.EntregaRepositorio;
import com.software2uis.msv_ordenes.servicio.EntregaService;
import com.software2uis.msv_ordenes.servicio.NotificacionService;

@RestController
@RequestMapping("/api/entrega")
public class EntregaController {

    private final EntregaService entregaService;
    private final NotificacionService notificacionService;
    private final EntregaRepositorio entregaRepository;

    public EntregaController(EntregaService entregaService, NotificacionService notificacionService, EntregaRepositorio entregaRepository) {
        this.entregaService = entregaService;
        this.notificacionService = notificacionService;
        this.entregaRepository = entregaRepository;
    }

    @PostMapping("/estimate")
    public ResponseEntity<?> estimateDelivery(@RequestBody Pedido pedido) {
        LocalDate estimatedDate = entregaService.calculateEstimatedDelivery(pedido);
        return ResponseEntity.ok("Fecha estimada de entrega: " + estimatedDate);
    }

    @PostMapping("/notify-delay")
    public ResponseEntity<?> notifyDelay(@RequestParam Long entregaId) {
        Optional<Entrega> entregaOpt = entregaRepository.findById(entregaId);

        if (entregaOpt.isPresent()) {
            Entrega entrega = entregaOpt.get();
            notificacionService.notifyDelay(entrega);
            return ResponseEntity.ok("Notificación de retraso enviada para el pedido: " + entrega.getPedido().getId());
        } else {
            return ResponseEntity.status(404).body("Entrega no encontrada");
        }
    }

    @PostMapping("/validate-address")
    public ResponseEntity<String> validateAddress(@RequestParam String calle, @RequestParam String barrio) {
        if (!isValidCalle(calle)) {
            return ResponseEntity.badRequest().body("La calle no cumple con el formato requerido.");
        }

        if (!isValidBarrio(barrio)) {
            return ResponseEntity.badRequest().body("El barrio no cumple con el formato requerido.");
        }

        return ResponseEntity.ok("La dirección es válida.");
    }

    private boolean isValidCalle(String calle) {
        // Patrón para la calle (e.g., "calle 14a #32b-62")
        String callePattern = "^(calle|carrera|avenida)\\s\\d+[a-zA-Z]?\\s?#\\d+[a-zA-Z]?-\\d+$";
        return calle.matches(callePattern);
    }

    private boolean isValidBarrio(String barrio) {
        // Validación básica para el nombre del barrio (solo letras y espacios)
        return barrio != null && barrio.matches("^[a-zA-Z\\s]+$") && !barrio.trim().isEmpty();
    }
}
