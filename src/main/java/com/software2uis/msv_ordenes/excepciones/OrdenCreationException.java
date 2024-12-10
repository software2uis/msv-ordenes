package com.software2uis.msv_ordenes.excepciones;

public class OrdenCreationException extends RuntimeException {
    public OrdenCreationException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
