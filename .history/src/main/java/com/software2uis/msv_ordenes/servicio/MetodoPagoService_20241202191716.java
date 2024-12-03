package com.software2uis.msv_ordenes.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software2uis.msv_ordenes.modelo.MetodoPago;
import com.software2uis.msv_ordenes.repositorio.MetodoPagoRepositorio;

@Service
public class MetodoPagoService {
    @Autowired
    private MetodoPagoRepositorio metodoPagoRepositorio;

    public List<MetodoPago> obtenerMetodosDePago() {
        return metodoPagoRepositorio.findAll();
    }

    public MetodoPago guardarMetodoPago(MetodoPago metodoPago) {
        return metodoPagoRepositorio.save(metodoPago);
    }

    public Optional<MetodoPago> obtenerMetodoPagoPorId(Long id) {
        return metodoPagoRepositorio.findById(id);
    }

    public void eliminarMetodoPago(Long id) {
        metodoPagoRepositorio.deleteById(id);
    }
}
