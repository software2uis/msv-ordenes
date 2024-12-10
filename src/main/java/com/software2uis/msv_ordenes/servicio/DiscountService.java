package com.software2uis.msv_ordenes.servicio;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DiscountService {


    public BigDecimal calcularDescuento(String codigo, BigDecimal total) {
        // Lógica para calcular descuento
        return BigDecimal.valueOf(20.00);
    }
}
