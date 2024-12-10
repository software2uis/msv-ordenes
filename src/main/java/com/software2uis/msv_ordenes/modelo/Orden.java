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

    private LocalDate fechaCreacion = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private EstadoOrden estado = EstadoOrden.PENDIENTE;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orden_id")
    private List<Producto> items;

    private BigDecimal total;

    private String direccionEnvio;
    private String ciudadEnvio;
    private String codigoPostalEnvio;
    private BigDecimal costoEnvio;
    private BigDecimal descuentoAplicado;
    private String tiempoEstimadoEntrega;
    @ManyToOne
    @JoinColumn(name = "metodo_pago_id")
    private MetodoPago metodoPago;
}
