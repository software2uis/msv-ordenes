package com.software2uis.msv_ordenes.repositorio;

import com.software2uis.msv_ordenes.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByNumeroCedula(String numeroCedula);

    Optional<Cliente> findByEmail(String email);
}
