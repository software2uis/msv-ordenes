package com.software2uis.msv_ordenes.servicio;

import com.software2uis.msv_ordenes.modelo.Cliente;
import com.software2uis.msv_ordenes.repositorio.ClienteRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepositorio clienteRepositorio;

    public ClienteService(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    public List<Cliente> obtenerTodos() {
        return clienteRepositorio.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepositorio.findById(id);
    }

    public Cliente guardar(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    public void eliminar(Long id) {
        clienteRepositorio.deleteById(id);
    }
}

