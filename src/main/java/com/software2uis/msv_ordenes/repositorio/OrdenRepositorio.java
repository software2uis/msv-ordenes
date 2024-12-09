package com.software2uis.msv_ordenes.repositorio;

import com.software2uis.msv_ordenes.modelo.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepositorio extends JpaRepository<Orden, Long> {
    List<Orden> findByClienteId(Long clienteId);
}
