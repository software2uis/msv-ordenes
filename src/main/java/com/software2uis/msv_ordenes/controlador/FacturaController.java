package com.software2uis.msv_ordenes.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.software2uis.msv_ordenes.modelo.Factura;
import com.software2uis.msv_ordenes.servicio.FacturaService;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping
    public ResponseEntity<Factura> generarFactura(@RequestBody FacturaRequest request) {
        // Verifica que el request no sea nulo y contiene los datos necesarios
        if (request == null || 
            request.getDetallesPedido() == null || 
            request.getCorreoUsuario() == null) {
            return ResponseEntity.badRequest().build();
        }

        Factura factura = facturaService.generarFactura(
                request.getDetallesPedido(),
                request.getSubtotal(),
                request.getImpuestos(),
                request.getDescuentos(),
                request.getCorreoUsuario()
        );

        return ResponseEntity.ok(factura);
    }
}
