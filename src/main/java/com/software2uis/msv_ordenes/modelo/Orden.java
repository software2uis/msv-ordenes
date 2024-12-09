package com.software2uis.msv_ordenes.modelo;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ordenes")
@Data
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fechaCreacion = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private EstadoOrden estado = EstadoOrden.PENDIENTE;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orden_id")
    private List<Producto> items;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    // Datos de envío
    @Column(nullable = false, length = 255)
    private String direccionEnvio;

    @Column(nullable = false, length = 100)
    private String ciudadEnvio;

    @Column(nullable = false, length = 10)
    private String codigoPostalEnvio;

    @Column(precision = 10, scale = 2)
    private BigDecimal costoEnvio;

    @Column(length = 50)
    private String tiempoEstimadoEntrega;
}
