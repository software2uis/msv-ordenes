package com.software2uis.msv_ordenes.controlador;

import com.software2uis.msv_ordenes.modelo.Orden;
import com.software2uis.msv_ordenes.repositorio.OrdenRepositorio;
import com.software2uis.msv_ordenes.servicio.OrdenServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    private final OrdenRepositorio ordenRepositorio;
    private final OrdenServicio ordenServicio;

    public OrdenController(
            OrdenRepositorio ordenRepositorio,
            OrdenServicio ordenServicio) {
        this.ordenRepositorio = ordenRepositorio;
        this.ordenServicio = ordenServicio;
    }

    /**
     * Obtener todas las órdenes.
     *
     * @return Lista de todas las órdenes.
     */
    @GetMapping
    public ResponseEntity<List<Orden>> listarTodas() {
        List<Orden> ordenes = ordenRepositorio.findAll();
        return ResponseEntity.ok(ordenes);
    }

    /**
     * Crear una nueva orden para un cliente específico.
     *
     * @param clienteId      ID del cliente.
     * @param ordenRequest   Datos de la orden.
     * @param metodoPagoTipo Tipo de método de pago.
     * @return Orden creada.
     */
    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<Orden> crearOrden(
            @PathVariable Long clienteId,
            @RequestBody Orden ordenRequest,
            @RequestParam String metodoPagoTipo) {

        Orden nuevaOrden = ordenServicio.crearOrden(clienteId, ordenRequest, metodoPagoTipo);
        return ResponseEntity.ok(nuevaOrden);
    }

    /**
     * Obtener una orden por su ID.
     *
     * @param id ID de la orden.
     * @return Orden encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Orden> obtenerPorId(@PathVariable Long id) {
        Optional<Orden> ordenOpt = ordenRepositorio.findById(id);
        return ordenOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Actualizar una orden existente.
     *
     * @param id               ID de la orden a actualizar.
     * @param ordenActualizada Datos actualizados de la orden.
     * @return Orden actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Orden> actualizarOrden(@PathVariable Long id, @RequestBody Orden ordenActualizada) {
        return ordenRepositorio.findById(id).map(orden -> {
            ordenActualizada.setId(orden.getId());
            Orden ordenGuardada = ordenRepositorio.save(ordenActualizada);
            return ResponseEntity.ok(ordenGuardada);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Eliminar una orden por su ID.
     *
     * @param id ID de la orden a eliminar.
     * @return Estado de la operación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable Long id) {
        if (ordenRepositorio.existsById(id)) {
            ordenRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Listar todas las órdenes de un cliente específico.
     *
     * @param clienteId ID del cliente.
     * @return Lista de órdenes del cliente.
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Orden>> listarPorClienteId(@PathVariable Long clienteId) {
        List<Orden> ordenes = ordenRepositorio.findByClienteId(clienteId);
        return ordenes.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(ordenes);
    }
}
