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

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    private Pedido pedido;

    private LocalDate fechaEstimada;
    private String estadoEntrega; // Estado de la entrega, por ejemplo, "EN PROCESO", "RETRASADA", etc.

    // Constructor vacío para JPA
    public Entrega() {
    }

    // Constructor con parámetros
    public Entrega(Pedido pedido, LocalDate fechaEstimada, String estadoEntrega) {
        this.pedido = pedido;
        this.fechaEstimada = fechaEstimada;
        this.estadoEntrega = estadoEntrega;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public LocalDate getFechaEstimada() {
        return fechaEstimada;
    }

    public void setFechaEstimada(LocalDate fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }

    public String getEstadoEntrega() {
        return estadoEntrega;
    }

    public void setEstadoEntrega(String estadoEntrega) {
        this.estadoEntrega = estadoEntrega;
    }

    @Override
    public String toString() {
        return "Entrega{" +
                "id=" + id +
                ", pedido=" + pedido +
                ", fechaEstimada=" + fechaEstimada +
                ", estadoEntrega='" + estadoEntrega + '\'' +
                '}';
    }
}
