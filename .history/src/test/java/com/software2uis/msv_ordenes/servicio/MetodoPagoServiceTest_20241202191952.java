package com.software2uis.msv_ordenes.servicio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.software2uis.msv_ordenes.modelo.MetodoPago;
import com.software2uis.msv_ordenes.repositorio.MetodoPagoRepositorio;

public class MetodoPagoServiceTest {
    @InjectMocks
    private MetodoPagoService metodoPagoService;

    @Mock
    private MetodoPagoRepositorio metodoPagoRepositorio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerMetodosDePago() {
        MetodoPago metodoPago1 = new MetodoPago();
        metodoPago1.setId(1L);
        metodoPago1.setTipo("Tarjeta de crédito");

        MetodoPago metodoPago2 = new MetodoPago();
        metodoPago2.setId(2L);
        metodoPago2.setTipo("Tarjeta de débito");

        when(metodoPagoRepositorio.findAll()).thenReturn(Arrays.asList(metodoPago1, metodoPago2));

        List<MetodoPago> metodos = metodoPagoService.obtenerMetodosDePago();

        assertEquals(2, metodos.size());
        verify(metodoPagoRepositorio, times(1)).findAll();
    }

    @Test
    public void testGuardarMetodoPago() {
        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setTipo("Tarjeta de crédito");

        when(metodoPagoRepositorio.save(metodoPago)).thenReturn(metodoPago);

        MetodoPago resultado = metodoPagoService.guardarMetodoPago(metodoPago);

        assertNotNull(resultado);
        assertEquals("Tarjeta de crédito", resultado.getTipo());
        verify(metodoPagoRepositorio, times(1)).save(metodoPago);
    }

    @Test
    public void testObtenerMetodoPagoPorId() {
        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setId(1L);
        metodoPago.setTipo("Tarjeta de crédito");

        when(metodoPagoRepositorio.findById(1L)).thenReturn(Optional.of(metodoPago));

        Optional<MetodoPago> resultado = metodoPagoService.obtenerMetodoPagoPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Tarjeta de crédito", resultado.get().getTipo());
        verify(metodoPagoRepositorio, times(1)).findById(1L);
    }
}
