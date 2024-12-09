package com.software2uis.msv_ordenes.servicio;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public boolean verificarTarjeta(String nombreTitular, String numeroTarjeta, String fechaExpiracion) {
        // Validaciones mínimas
        return numeroTarjeta != null && !numeroTarjeta.isEmpty();
    }

    public boolean realizarPago(OrdenService ordenService) {
        // Aquí integrarías tu lógica de pago real con un gateway.
        return true;
    }
}
