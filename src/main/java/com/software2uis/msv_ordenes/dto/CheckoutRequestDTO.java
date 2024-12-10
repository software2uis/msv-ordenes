package com.software2uis.msv_ordenes.dto;

import lombok.Data;

@Data
public class CheckoutRequestDTO {
    private ClienteDTO cliente;
    private DireccionDTO direccionEnvio;
    private MetodoPagoDTO metodoPago;
    private String codigoDescuento;
    private DatosTarjetaDTO datosTarjeta;
}

@Data
class ClienteDTO {
    private String nombres;
    private String apellidos;
    private String numeroCedula;
    private String email;
    private String numeroCelular;
}

@Data
class DireccionDTO {
    private String direccionEnvio;
    private String ciudadEnvio;
    private String codigoPostalEnvio;
    private String pais;
}

@Data
class MetodoPagoDTO {
    private String tipo;
}

@Data
class DatosTarjetaDTO {
    private String nombreTitular;
    private String numeroTarjeta;
    private String fechaExpiracion;
}
