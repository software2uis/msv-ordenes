package com.software2uis.msv_ordenes.controlador;

public class FacturaRequest {
    private String detallesPedido;
    private double subtotal;
    private double impuestos;
    private double descuentos;
    private String correoUsuario;

    // Getters y Setters
    public String getDetallesPedido() {
        return detallesPedido;
    }

    public void setDetallesPedido(String detallesPedido) {
        this.detallesPedido = detallesPedido;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(double impuestos) {
        this.impuestos = impuestos;
    }

    public double getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(double descuentos) {
        this.descuentos = descuentos;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }
}
