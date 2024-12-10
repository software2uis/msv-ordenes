package com.software2uis.msv_ordenes.controlador;

import com.software2uis.msv_ordenes.dto.CheckoutRequestDTO;
import com.software2uis.msv_ordenes.modelo.Cliente;
import com.software2uis.msv_ordenes.modelo.Factura;
import com.software2uis.msv_ordenes.servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
@CrossOrigin(origins = "*")
public class CheckoutController {
    @Autowired
    private SesionService sesionService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private OrdenServicio ordenService;

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private MetodoPagoService metodoPagoService;

    @Autowired
    private ShippingService shippingService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/session/{username}")
    public ResponseEntity<Map<String, Object>> checkSession(@PathVariable String username) {
        sesionService.checkActiveSession(username);
        Map<String, Object> response = new HashMap<>();
        response.put("isActiveSession", sesionService.isActiveSession());
        response.put("activeUsername", sesionService.getActiveUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cliente")
    public ResponseEntity<Map<String, Object>> registrarCliente(@RequestBody Cliente cliente) {
        Cliente guardado = clienteService.guardar(cliente);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Cliente registrado.");
        response.put("clienteId", guardado.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/shipping")
    public ResponseEntity<Map<String, Object>> calcularEnvio(@RequestBody Map<String, String> direccion) {
        String direccionEnvio = direccion.get("direccionEnvio");
        String ciudadEnvio = direccion.get("ciudadEnvio");
        String codigoPostal = direccion.get("codigoPostalEnvio");
        String pais = direccion.get("pais");

        BigDecimal costoEnvio = BigDecimal.valueOf(shippingService.calcularCostoEnvio(ciudadEnvio, pais));
        String tiempoEntrega = shippingService.calcularTiempoEntrega(ciudadEnvio, pais);

        ordenService.setShippingData(direccionEnvio, ciudadEnvio, codigoPostal, costoEnvio, tiempoEntrega);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Envío calculado.");
        response.put("costoEnvio", costoEnvio);
        response.put("tiempoEstimadoEntrega", tiempoEntrega);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/summary/{username}")
    public ResponseEntity<Map<String, Object>> resumenCompra(@PathVariable String username) {
        sesionService.checkActiveSession(username);
        if (!sesionService.isActiveSession()) {
            return ResponseEntity.status(401).body(Map.of("message", "Sesión inactiva."));
        }

        Map<String, Object> summary = ordenService.getOrderSummary(username);
        return ResponseEntity.ok(summary);
    }

    @PostMapping("/discount")
    public ResponseEntity<Map<String, Object>> aplicarDescuento(@RequestBody Map<String, String> body) {
        String codigo = body.get("codigo");
        try {
            BigDecimal nuevoTotal = discountService.calcularDescuento(codigo, ordenService.getCurrentTotal());
            ordenService.applyDiscount(codigo, ordenService);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Descuento aplicado.");
            response.put("nuevoTotal", nuevoTotal);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/payment-details")
    public ResponseEntity<Map<String, Object>> ingresarDatosTarjeta(@RequestBody Map<String, String> datosTarjeta) {
        String nombreTitular = datosTarjeta.get("nombreTitular");
        String numeroTarjeta = datosTarjeta.get("numeroTarjeta");
        String fechaExp = datosTarjeta.get("fechaExpiracion");

        boolean verificado = paymentService.verificarTarjeta(nombreTitular, numeroTarjeta, fechaExp);
        if (!verificado) {
            return ResponseEntity.badRequest().body(Map.of("message", "Tarjeta inválida."));
        }

        return ResponseEntity.ok(Map.of("message", "Tarjeta verificada."));
    }

    @PostMapping("/pay")
    public ResponseEntity<Map<String, Object>> procesarPago() {
        try {
            boolean pagado = paymentService.realizarPago(ordenService);
            if (!pagado) {
                ordenService.revertirStock();
                return ResponseEntity.status(400).body(Map.of("message", "Pago fallido."));
            }

            Factura factura = ordenService.generarFactura();
            facturaService.enviarFacturaPorCorreo(factura, factura.getCliente().getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Pago exitoso.");
            response.put("numeroFactura", factura.getNumeroFactura());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Error en el procesamiento del pago."));
        }
    }
}
