package com.software2uis.msv_ordenes.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregaRepositorio extends JpaRepository<Entrega, Long> {
    // Métodos adicionales 
}
