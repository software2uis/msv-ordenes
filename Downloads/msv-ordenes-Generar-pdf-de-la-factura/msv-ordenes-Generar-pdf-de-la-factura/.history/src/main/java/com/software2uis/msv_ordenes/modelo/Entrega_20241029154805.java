package com.software2uis.msv_ordenes.modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaEstimada;
    private String estado;
    private String detalles;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    // Getters y Setters
}
