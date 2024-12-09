package com.software2uis.msv_ordenes.modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;

@Entity
@Table(name = "productos")
@Data
public class Producto {

    @Id
    private String id; // ID proporcionado por el microservicio de Catálogo
    
    private String nombre;

    private BigDecimal precio;

    private Integer cantidad;

}