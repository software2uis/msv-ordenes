package com.software2uis.msv_ordenes.servicio;

import org.springframework.stereotype.Service;

@Service
public class ShippingService {

    public double calcularCostoEnvio(String ciudad, String pais) {
        // Lógica mock:
        // Colombia: Bucaramanga = 5000, otra ciudad = 15000
        // Internacional = 50000
        if ("colombia".equalsIgnoreCase(pais)) {
            if ("bucaramanga".equalsIgnoreCase(ciudad)) {
                return 5000.00;
            } else {
                return 15000.00;
            }
        } else {
            return 50000.00;
        }
    }

    public String calcularTiempoEntrega(String ciudad, String pais) {
        if ("colombia".equalsIgnoreCase(pais)) {
            if ("bucaramanga".equalsIgnoreCase(ciudad)) return "1 día";
            return "3 días";
        }
        return "7 días";
    }
}
