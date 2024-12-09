package com.software2uis.msv_ordenes.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "metodos_pago")
@Data
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoMetodoPago tipo; // TARJETA_CREDITO, TARJETA_DEBITO, etc.

    @Column(nullable = false, length = 100)
    private String nombreTitular;

    @Column(nullable = false, unique = true, length = 20)
    private String numeroTarjeta;

    @Column(nullable = false, length = 10)
    private String fechaExpiracion; // Formato: MM/AA o MM/AAAA

    // Relación con el cliente
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
