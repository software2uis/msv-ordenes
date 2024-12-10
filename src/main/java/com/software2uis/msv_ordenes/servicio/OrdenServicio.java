package com.software2uis.msv_ordenes.servicio;

import com.software2uis.msv_ordenes.excepciones.ClienteNoEncontradoException;
import com.software2uis.msv_ordenes.excepciones.OrdenCreationException;
import com.software2uis.msv_ordenes.modelo.*;
import com.software2uis.msv_ordenes.repositorio.FacturaRepositorio;
import com.software2uis.msv_ordenes.repositorio.OrdenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdenServicio {
    @Autowired
    private OrdenRepositorio ordenRepositorio;

    @Autowired
    private FacturaRepositorio facturaRepositorio;

    @Autowired
    private CarritoIntegrationService carritoIntegrationService;

    @Autowired
    private CatalogIntegrationService catalogIntegrationService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private SesionService sesionService;

    @Autowired
    private MetodoPagoService metodoPagoService;

    private List<Producto> productos;
    private BigDecimal descuentoAplicado = BigDecimal.ZERO;
    private BigDecimal costoEnvio = BigDecimal.ZERO;
    private BigDecimal impuestos = BigDecimal.ZERO;
    private BigDecimal subtotal = BigDecimal.ZERO;

    private Orden ordenActual;
    @Autowired
    private DiscountService discountService;

    @Transactional
    public Orden crearOrden(Long clienteId, Orden ordenRequest, String metodoPagoTipo) {
        sesionService.checkActiveSession(ordenRequest.getCliente().getEmail());
        if (!sesionService.isActiveSession()) {
            throw new OrdenCreationException("No hay sesión activa.", null);
        }

        Cliente cliente = clienteService.buscarPorId(clienteId)
                .orElseThrow(() -> new ClienteNoEncontradoException("Cliente no encontrado."));

        if (cliente.getEmail() == null || !cliente.getEmail().equals(sesionService.getActiveUsername())) {
            throw new OrdenCreationException("Usuario no coincide con el cliente.", null);
        }

        List<Producto> productosOrden = ordenRequest.getItems();
        if (productosOrden == null || productosOrden.isEmpty()) {
            throw new OrdenCreationException("La orden debe tener al menos un producto.", null);
        }

        calcularTotales(productosOrden, ordenRequest.getCostoEnvio(), ordenRequest.getDescuentoAplicado());

        EstadoOrden estado = ordenRequest.getEstado() != null ? ordenRequest.getEstado() : EstadoOrden.PENDIENTE;

        MetodoPago metodoPago = metodoPagoService.seleccionarMetodoPago(metodoPagoTipo, cliente);

        Orden nuevaOrden = new Orden();
        nuevaOrden.setCliente(cliente);
        nuevaOrden.setFechaCreacion(LocalDate.now());
        nuevaOrden.setEstado(estado);
        nuevaOrden.setItems(productosOrden);
        nuevaOrden.setTotal(getCurrentTotal());
        nuevaOrden.setDireccionEnvio(ordenRequest.getDireccionEnvio());
        nuevaOrden.setCiudadEnvio(ordenRequest.getCiudadEnvio());
        nuevaOrden.setCodigoPostalEnvio(ordenRequest.getCodigoPostalEnvio());
        nuevaOrden.setCostoEnvio(costoEnvio);
        nuevaOrden.setDescuentoAplicado(descuentoAplicado);
        nuevaOrden.setTiempoEstimadoEntrega(ordenRequest.getTiempoEstimadoEntrega());

        try {
            nuevaOrden = ordenRepositorio.save(nuevaOrden);
            ordenActual = nuevaOrden;

            Factura factura = generarFactura(nuevaOrden, cliente, metodoPago);
            facturaRepositorio.save(factura);

            //facturaService.enviarFacturaPorCorreo(factura, cliente.getEmail());

            return nuevaOrden;
        } catch (Exception e) {
            revertirStock();
            throw new OrdenCreationException("Error al crear la orden.", e);
        }
    }

    public void setShippingData(String direccionEnvio, String ciudadEnvio, String codigoPostal, BigDecimal costoEnvio, String tiempoEntrega) {
        if (ordenActual == null) {
            throw new OrdenCreationException("No hay una orden activa.", null);
        }
        ordenActual.setDireccionEnvio(direccionEnvio);
        ordenActual.setCiudadEnvio(ciudadEnvio);
        ordenActual.setCodigoPostalEnvio(codigoPostal);
        ordenActual.setCostoEnvio(costoEnvio);
        ordenActual.setTiempoEstimadoEntrega(tiempoEntrega);
        ordenRepositorio.save(ordenActual);
    }

    public Map<String, Object> getOrderSummary(String username) {
        Cliente cliente = clienteService.buscarPorEmail(username)
                .orElseThrow(() -> new ClienteNoEncontradoException("Cliente no encontrado."));
        List<Orden> ordenes = ordenRepositorio.findByClienteId(cliente.getId());
        if (ordenes.isEmpty()) {
            throw new OrdenCreationException("No hay órdenes para el cliente.", null);
        }
        Orden orden = ordenes.get(0);
        Map<String, Object> summary = new HashMap<>();
        summary.put("ordenId", orden.getId());
        summary.put("fechaCreacion", orden.getFechaCreacion());
        summary.put("estado", orden.getEstado());
        summary.put("direccionEnvio", orden.getDireccionEnvio());
        summary.put("ciudadEnvio", orden.getCiudadEnvio());
        summary.put("codigoPostalEnvio", orden.getCodigoPostalEnvio());
        summary.put("costoEnvio", orden.getCostoEnvio());
        summary.put("tiempoEstimadoEntrega", orden.getTiempoEstimadoEntrega());
        summary.put("total", orden.getTotal());
        summary.put("items", orden.getItems());
        return summary;
    }

    public BigDecimal applyDiscount(String codigo, OrdenServicio ordenService) {
        if (ordenActual == null) {
            throw new OrdenCreationException("No hay una orden activa.", null);
        }
        BigDecimal descuento = discountService.calcularDescuento(codigo, ordenActual.getTotal());
        ordenActual.setDescuentoAplicado(descuento);
        ordenActual.setTotal(ordenActual.getTotal().subtract(descuento));
        ordenRepositorio.save(ordenActual);
        return ordenActual.getTotal();
    }

    public Factura generarFactura() {
        if (ordenActual == null) {
            throw new OrdenCreationException("No hay una orden activa.", null);
        }
        Cliente cliente = ordenActual.getCliente();
        MetodoPago metodoPago = ordenActual.getMetodoPago();
        return generarFactura(ordenActual, cliente, metodoPago);
    }

    private void calcularTotales(List<Producto> productosOrden, BigDecimal costoEnvio, BigDecimal descuento) {
        this.productos = productosOrden;
        this.descuentoAplicado = descuento != null ? descuento : BigDecimal.ZERO;
        this.costoEnvio = costoEnvio != null ? costoEnvio : BigDecimal.ZERO;

        this.subtotal = productosOrden.stream()
                .map(p -> p.getPrecio().multiply(BigDecimal.valueOf(p.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.impuestos = subtotal.multiply(BigDecimal.valueOf(0.19));
    }

    public BigDecimal getCurrentTotal() {
        return subtotal.add(impuestos).add(costoEnvio).subtract(descuentoAplicado);
    }

    private Factura generarFactura(Orden orden, Cliente cliente, MetodoPago metodoPago) {
        Factura factura = new Factura();
        factura.setNumeroFactura("FCT-" + System.currentTimeMillis());
        factura.setCliente(cliente);
        factura.setOrden(orden);
        factura.setSubtotal(subtotal);
        factura.setImpuestos(impuestos);
        factura.setDescuentos(descuentoAplicado);
        factura.setTotal(getCurrentTotal());
        factura.setFechaEmision(LocalDate.now());
        factura.setMetodoPago(metodoPago);
        factura.setDetallesPedido("Detalle de la compra");
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
