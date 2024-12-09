package com.software2uis.msv_ordenes.repositorio;

import com.software2uis.msv_ordenes.modelo.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacturaRepositorio extends JpaRepository<Factura, Long> {

    // Buscar factura por número de factura
    Optional<Factura> findByNumeroFactura(String numeroFactura);

}
