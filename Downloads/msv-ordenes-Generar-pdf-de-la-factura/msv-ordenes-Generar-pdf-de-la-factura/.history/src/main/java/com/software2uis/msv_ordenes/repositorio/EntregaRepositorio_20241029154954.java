package com.software2uis.msv_ordenes.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;

import com.software2uis.msv_ordenes.modelo.Entrega;

public interface EntregaRepositorio extends JpaRepository<Entrega, Long> {
}
