package com.software2uis.msv_ordenes.repositorio;

import com.software2uis.msv_ordenes.modelo.MetodoPago;
import com.software2uis.msv_ordenes.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetodoPagoRepositorio extends JpaRepository<MetodoPago, Long> {

    // Buscar métodos de pago por cliente
    List<MetodoPago> findByCliente(Cliente cliente);

    // Buscar por número de tarjeta
    Optional<MetodoPago> findByNumeroTarjeta(String numeroTarjeta);
}
