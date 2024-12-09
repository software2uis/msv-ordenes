package com.software2uis.msv_ordenes.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.software2uis.msv_ordenes.servicio.*;

import com.software2uis.msv_ordenes.modelo.Cliente;
import com.software2uis.msv_ordenes.modelo.Factura;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
@CrossOrigin(origins = "*")
public class CheckoutController {

    private final SesionService sesionService;
    private final ClienteService clienteService;
    private final OrdenService ordenService;
    private final FacturaService facturaService;
    private final MetodoPagoService metodoPagoService;
    private final ShippingService shippingService;
    private final DiscountService discountService;
    private final PaymentService paymentService;

    @Autowired
    public CheckoutController(
            SesionService sesionService,
            ClienteService clienteService,
            OrdenService ordenService,
            FacturaService facturaService,
            MetodoPagoService metodoPagoService,
            ShippingService shippingService,
            DiscountService discountService,
            PaymentService paymentService) {
        this.sesionService = sesionService;
        this.clienteService = clienteService;
        this.ordenService = ordenService;
        this.facturaService = facturaService;
        this.metodoPagoService = metodoPagoService;
        this.shippingService = shippingService;
        this.discountService = discountService;
        this.paymentService = paymentService;
    }

    @GetMapping("/session")
    public ResponseEntity<Map<String, Object>> checkSession() {
        sesionService.checkActiveSession();
        Map<String, Object> response = new HashMap<>();
        response.put("isActiveSession", sesionService.isActiveSession());
        response.put("activeUsername", sesionService.getActiveUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cliente")
    public ResponseEntity<Map<String, Object>> registrarCliente(@RequestBody Cliente cliente) {
        Cliente guardado = clienteService.guardar(cliente);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Datos del cliente guardados correctamente");
        response.put("clienteId", guardado.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/shipping")
    public ResponseEntity<Map<String, Object>> calcularEnvio(@RequestBody Map<String, String> direccion) {
        String direccionEnvio = direccion.get("direccionEnvio");
        String ciudadEnvio = direccion.get("ciudadEnvio");
        String codigoPostal = direccion.get("codigoPostalEnvio");
        String pais = direccion.get("pais");

        double costoEnvio = shippingService.calcularCostoEnvio(ciudadEnvio, pais);
        String tiempoEntrega = shippingService.calcularTiempoEntrega(ciudadEnvio, pais);

        ordenService.setShippingData(direccionEnvio, ciudadEnvio, codigoPostal, costoEnvio, tiempoEntrega);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Costo de envío calculado");
        response.put("costoEnvio", costoEnvio);
        response.put("tiempoEstimadoEntrega", tiempoEntrega);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> resumenCompra() {
        Map<String, Object> summary = ordenService.getOrderSummary();
        return ResponseEntity.ok(summary);
    }

    @PostMapping("/discount")
    public ResponseEntity<Map<String, Object>> aplicarDescuento(@RequestBody Map<String, String> body) {
        String codigo = body.get("codigo");
        double nuevoTotal = discountService.applyDiscount(codigo, ordenService);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Cupón aplicado");
        response.put("nuevoTotal", nuevoTotal);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/payment-details")
    public ResponseEntity<Map<String, Object>> ingresarDatosTarjeta(@RequestBody Map<String, String> datosTarjeta) {
        String nombreTitular = datosTarjeta.get("nombreTitular");
        String numeroTarjeta = datosTarjeta.get("numeroTarjeta");
        String fechaExp = datosTarjeta.get("fechaExpiracion");

        boolean verificado = paymentService.verificarTarjeta(nombreTitular, numeroTarjeta, fechaExp);
        if (!verificado) {
            return ResponseEntity.badRequest().body(Map.of("message", "Tarjeta no válida"));
        }

        return ResponseEntity.ok(Map.of("message", "Datos de tarjeta guardados y verificados"));
    }

    @PostMapping("/pay")
    public ResponseEntity<Map<String, Object>> procesarPago() {
        boolean pagado = paymentService.realizarPago(ordenService);
        if (!pagado) {
            // Si el pago falla, se podría revertir el stock
            ordenService.revertirStock();
            return ResponseEntity.status(400).body(Map.of("message", "No se pudo procesar el pago"));
        }

        Factura factura = ordenService.generarFactura();
        try {
            facturaService.enviarFacturaPorCorreo(factura, factura.getCliente().getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Map.of("message", "Pago realizado, pero error al enviar la factura."));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Pago realizado con éxito. Factura enviada al correo " + factura.getCliente().getEmail() + ".",
                "numeroFactura", factura.getNumeroFactura()
        ));
    }
}
