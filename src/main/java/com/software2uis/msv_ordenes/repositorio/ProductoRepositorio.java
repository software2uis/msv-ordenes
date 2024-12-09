package com.software2uis.msv_ordenes.repositorio;

import com.software2uis.msv_ordenes.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, String> {
}
