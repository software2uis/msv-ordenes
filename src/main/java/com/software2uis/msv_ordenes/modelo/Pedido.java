package com.software2uis.msv_ordenes.modelo;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaCreacion;

    private String estado; // Por ejemplo: "PENDIENTE", "CONFIRMADO", "ENVIADO"

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> items;

    private BigDecimal total;

    // Datos de envío
    private String direccionEnvio;
    private String ciudadEnvio;
    private String codigoPostalEnvio;
    private String instrucciones;

    private BigDecimal costoEnvio;
    private String tiempoEstimadoEntrega;

    // No es necesario agregar getters y setters manualmente debido a @Data
}
