package com.software2uis.msv_ordenes.modelo;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Data
public class Producto {
    @Id
    private String id;

    private String nombre;
    private BigDecimal precio;
    private int cantidad;
}
