package com.software2uis.msv_ordenes.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.software2uis.msv_ordenes.modelo.Factura;

@Repository
public interface FacturaRepositorio extends JpaRepository<Factura, Long> {
    
}
