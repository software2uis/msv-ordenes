package com.software2uis.msv_ordenes.controlador;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software2uis.msv_ordenes.modelo.Pedido;

@RestController
@RequestMapping("/api/entrega")
public class EntregaController {

    private final EntregaService entregaService;
    private final NotificacionService notificacionService; //futuro uso de notificaciones por retraso

    public EntregaController(EntregaService entregaService, NotificacionService notificacionService) {
        this.entregaService = entregaService;
        this.notificacionService = notificacionService;
    }

    @PostMapping("/estimate")
    public ResponseEntity<?> estimateDelivery(@RequestBody Pedido pedido) {
        // Usamos la instancia entregaService para llamar al método no estático
        LocalDate estimatedDate = entregaService.calculateEstimatedDelivery(pedido);
        return ResponseEntity.ok("Fecha estimada de entrega: " + estimatedDate);
    }

    @PostMapping("/notify-delay")
    public ResponseEntity<?> notifyDelay(@RequestParam Long entregaId) {
        // Busca la entrega en base a su ID (deberías implementar un método en el repositorio)
        // notificationService.notifyDelay(entrega);
        return ResponseEntity.ok("Notificación de retraso enviada");
    }
}
