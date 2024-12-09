package com.software2uis.msv_ordenes.controlador;

import com.software2uis.msv_ordenes.modelo.Producto;
import com.software2uis.msv_ordenes.servicio.ProductoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/{id}")
    public Producto obtenerProducto(@PathVariable String id) {
        return productoService.obtenerProductoDeCatalogo(id);
    }
}