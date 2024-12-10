package com.software2uis.msv_ordenes.servicio;

import com.software2uis.msv_ordenes.servicio.OrdenServicio;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public boolean verificarTarjeta(String nombreTitular, String numeroTarjeta, String fechaExpiracion) {
        // Lógica de verificación de tarjeta
        return true;
    }

    public boolean realizarPago(OrdenServicio ordenService) {
        // Lógica para realizar el pago
        return true;
    }
}
