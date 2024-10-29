package com.software2uis.msv_ordenes.servicio;

import org.springframework.stereotype.Service;

import com.software2uis.msv_ordenes.modelo.Entrega;

@Service
public class NotificacionService {

    public void notifyDelay(Entrega entrega) {
        // Lógica para enviar notificación al usuario sobre el retraso en la entrega
        System.out.println("Notificación de retraso enviada para el pedido: " + entrega.getPedido().getId());
    }
}
