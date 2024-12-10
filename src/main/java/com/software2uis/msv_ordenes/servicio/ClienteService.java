package com.software2uis.msv_ordenes.servicio;

import com.software2uis.msv_ordenes.modelo.Cliente;
import com.software2uis.msv_ordenes.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public Cliente guardar(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepositorio.findById(id);
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepositorio.findByEmail(email);
    }
}
