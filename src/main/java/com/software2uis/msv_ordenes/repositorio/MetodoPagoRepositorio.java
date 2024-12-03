package com.software2uis.msv_ordenes.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.software2uis.msv_ordenes.modelo.MetodoPago;

public interface MetodoPagoRepositorio extends JpaRepository<MetodoPago, Long> {
    
}
