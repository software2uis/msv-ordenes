package com.software2uis.msv_ordenes.servicio;

import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    public double applyDiscount(String codigo, OrdenService ordenService) {
        if ("DESCUENTO10".equalsIgnoreCase(codigo)) {
            double descuento = 10000.0;
            ordenService.setDiscount(descuento);
            return ordenService.getCurrentTotal();
        }
        return ordenService.getCurrentTotal();
    }
}
