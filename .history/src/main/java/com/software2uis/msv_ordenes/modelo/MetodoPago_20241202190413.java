package com.software2uis.msv_ordenes.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class MetodoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo; // Ejemplo: "Tarjeta de crédito", "Tarjeta de débito"

    @Column(nullable = false)
    private String numeroTarjeta;

    @Column(nullable = false)
    private String fechaExpiracion;

    @Column(nullable = false)
    private String nombreTitular;

    // Getters y setters
}
