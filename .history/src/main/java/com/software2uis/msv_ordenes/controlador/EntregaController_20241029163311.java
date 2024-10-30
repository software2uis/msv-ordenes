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
}

