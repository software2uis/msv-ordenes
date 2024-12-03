package com.software2uis.msv_ordenes.modelo;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "items_pedido")
@Data
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Pedido
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    private Long productoId; // ID del producto en el microservicio de Catálogo

    private String nombreProducto;

    private BigDecimal precioUnitario;

    private Integer cantidad;

    private BigDecimal subtotal;

    // No es necesario agregar getters y setters manualmente debido a @Data
}
