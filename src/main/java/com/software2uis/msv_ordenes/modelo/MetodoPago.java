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
    private TipoMetodoPago tipo;

    private String nombreTitular;
    private String numeroTarjeta;
    private String fechaExpiracion;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
