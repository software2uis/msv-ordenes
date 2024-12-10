package com.software2uis.msv_ordenes.servicio;

import com.software2uis.msv_ordenes.modelo.MetodoPago;
import com.software2uis.msv_ordenes.modelo.Cliente;
import com.software2uis.msv_ordenes.modelo.TipoMetodoPago;
import com.software2uis.msv_ordenes.repositorio.MetodoPagoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public MetodoPago seleccionarMetodoPago(String tipo, Cliente cliente) {
        TipoMetodoPago tipoEnum;
        try {
            tipoEnum = TipoMetodoPago.valueOf(tipo);
        } catch (Exception e) {
            throw new IllegalArgumentException("Tipo de método de pago inválido: " + tipo);
        }

        Optional<MetodoPago> metodoOpt = metodoPagoRepositorio.findByTipoAndCliente(tipoEnum, cliente);
        if (metodoOpt.isPresent()) {
            return metodoOpt.get();
        }

        MetodoPago mp = new MetodoPago();
        mp.setTipo(tipoEnum);
        mp.setNombreTitular("N/A");
        mp.setNumeroTarjeta("0000000000000000");
        mp.setFechaExpiracion("01/30");
        mp.setCliente(cliente);
        return metodoPagoRepositorio.save(mp);
    }
}
