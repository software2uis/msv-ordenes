package com.software2uis.msv_ordenes.servicio;

import com.software2uis.msv_ordenes.modelo.Factura;
import com.software2uis.msv_ordenes.modelo.Producto;
import com.software2uis.msv_ordenes.repositorio.OrdenRepositorio;
import com.software2uis.msv_ordenes.dto.CartProductDTO;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

@Service
public class OrdenService {

    private final OrdenRepositorio ordenRepositorio;
    private final CarritoIntegrationService carritoIntegrationService;
    private final CatalogIntegrationService catalogIntegrationService;
    private final SesionService sesionService;

    private List<Producto> productos;
    private double descuentoAplicado = 0.0;
    private double costoEnvio = 0.0;
    private double impuestos = 0.0;
    private double subtotal = 0.0;

    private String direccionEnvio;
    private String ciudadEnvio;
    private String codigoPostalEnvio;
    private String tiempoEstimadoEntrega;

    public OrdenService(OrdenRepositorio ordenRepositorio,
                        CarritoIntegrationService carritoIntegrationService,
                        CatalogIntegrationService catalogIntegrationService,
                        SesionService sesionService) {
        this.ordenRepositorio = ordenRepositorio;
        this.carritoIntegrationService = carritoIntegrationService;
        this.catalogIntegrationService = catalogIntegrationService;
        this.sesionService = sesionService;
    }

    public void setShippingData(String direccionEnvio, String ciudadEnvio, String codigoPostal, double costoEnvio, String tiempoEntrega) {
        this.direccionEnvio = direccionEnvio;
        this.ciudadEnvio = ciudadEnvio;
        this.codigoPostalEnvio = codigoPostal;
        this.costoEnvio = costoEnvio;
        this.tiempoEstimadoEntrega = tiempoEntrega;
    }

    public Map<String, Object> getOrderSummary() {
        sesionService.checkActiveSession();
        if (!sesionService.isActiveSession()) {
            throw new RuntimeException("No hay sesión activa.");
        }
        String username = sesionService.getActiveUsername();
        List<CartProductDTO> cartProducts = carritoIntegrationService.obtenerCarritoDelUsuario(username);

        // Convertir CartProductDTO a Producto
        this.productos = cartProducts.stream().map(cp -> {
            Producto p = new Producto();
            p.setId(cp.getId());
            p.setNombre(cp.getName());
            p.setPrecio(BigDecimal.valueOf(cp.getPrice()));
            p.setCantidad(cp.getQuantity());
            return p;
        }).collect(Collectors.toList());

        // Reservar stock en catálogo
        for (Producto p : productos) {
            catalogIntegrationService.subtractQuantity(p.getId(), p.getCantidad());
        }

        // Calcular subtotal, impuestos, total
        subtotal = productos.stream()
                .mapToDouble(p -> p.getPrecio().doubleValue() * p.getCantidad())
                .sum();

        impuestos = subtotal * 0.19; // Ej: 19% IVA
        double total = subtotal + impuestos + costoEnvio - descuentoAplicado;

        return Map.of(
                "productos", productos,
                "subtotal", subtotal,
                "impuestos", impuestos,
                "descuento", descuentoAplicado,
                "costoEnvio", costoEnvio,
                "total", total
        );
    }

    public double getCurrentTotal() {
        return subtotal + impuestos + costoEnvio - descuentoAplicado;
    }

    public void setDiscount(double descuento) {
        this.descuentoAplicado = descuento;
    }

    public Factura generarFactura() {
        Factura factura = new Factura();
        factura.setNumeroFactura("FCT-" + System.currentTimeMillis());
        factura.setSubtotal(BigDecimal.valueOf(subtotal));
        factura.setImpuestos(BigDecimal.valueOf(impuestos));
        factura.setDescuentos(BigDecimal.valueOf(descuentoAplicado));
        factura.setTotal(BigDecimal.valueOf(getCurrentTotal()));
        factura.setFechaEmision(LocalDate.now());
        factura.setDetallesPedido("Detalle de la compra");
        // Aquí podrías setear cliente y orden reales si ya los tienes.
        return factura;
    }

    public void revertirStock() {
        if (productos != null) {
            for (Producto p : productos) {
                catalogIntegrationService.addQuantity(p.getId(), p.getCantidad());
            }
        }
    }
}
