package com.software2uis.msv_ordenes.servicio;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software2uis.msv_ordenes.modelo.Factura;
import com.software2uis.msv_ordenes.repositorio.FacturaRepositorio;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepositorio facturaRepositorio;

    @Autowired
    private EmailService emailService; 
public Factura generarFactura(String detallesPedido, Double subtotal, Double impuestos, Double descuentos, String correoUsuario) {
    Factura factura = new Factura();
    factura.setNumeroFactura("FAC-" + System.currentTimeMillis());
    factura.setFechaEmision(LocalDate.now());
    factura.setDetallesPedido(detallesPedido);
    factura.setSubtotal(subtotal);
    factura.setImpuestos(impuestos);
    factura.setDescuentos(descuentos);
    factura.setTotal(subtotal + impuestos - descuentos);
    factura.setCorreoUsuario(correoUsuario);

    Factura facturaGuardada = facturaRepositorio.save(factura);

    // Enviar la factura por correo
    emailService.enviarFacturaPorCorreo(facturaGuardada);

    return facturaGuardada;
}
}
