package com.software2uis.msv_ordenes.excepciones;

public class ClienteNoEncontradoException extends RuntimeException {
    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
