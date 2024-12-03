package com.software2uis.msv_ordenes.repositorio;



import org.springframework.data.jpa.repository.JpaRepository;

import com.software2uis.msv_ordenes.modelo.Pedido;

public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {
    
}

