package com.software2uis.msv_ordenes.controlador;

import com.software2uis.msv_ordenes.servicio.FacturaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController() {
        this.facturaService = new FacturaService();
    }

    @GetMapping("/factura")
    public ResponseEntity<byte[]> generarFactura(
            @RequestParam String cliente,
            @RequestParam String producto,
            @RequestParam double precio,
            @RequestParam int cantidad) {

        byte[] factura = facturaService.generarFactura(cliente, producto, precio, cantidad);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=factura.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(factura);
    }
}
