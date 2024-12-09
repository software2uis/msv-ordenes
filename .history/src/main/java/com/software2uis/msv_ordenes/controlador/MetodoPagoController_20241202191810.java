package com.software2uis.msv_ordenes.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.software2uis.msv_ordenes.servicio.MetodoPagoService;

@RestController
@RequestMapping("/api/metodos-pago")
public class MetodoPagoController {
    @Autowired
    private MetodoPagoService metodoPagoService;

    @GetMapping
    public List<MetodoPago> obtenerMetodosDePago() {
        return metodoPagoService.obtenerMetodosDePago();
    }

    @PostMapping
    public MetodoPago guardarMetodoPago(@RequestBody MetodoPago metodoPago) {
        return metodoPagoService.guardarMetodoPago(metodoPago);
    }

    @GetMapping("/{id}")
    public Optional<MetodoPago> obtenerMetodoPagoPorId(@PathVariable Long id) {
        return metodoPagoService.obtenerMetodoPagoPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarMetodoPago(@PathVariable Long id) {
        metodoPagoService.eliminarMetodoPago(id);
    }
}
