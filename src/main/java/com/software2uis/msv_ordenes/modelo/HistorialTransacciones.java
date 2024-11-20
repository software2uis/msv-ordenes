package com.software2uis.msv_ordenes.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class HistorialTransacciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long transaccionId;

    private String accion; // Ej.: "CANCELADA"

    private LocalDateTime fecha;

    // Otros campos y getters/setters
}



