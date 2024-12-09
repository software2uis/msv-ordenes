package com.software2uis.msv_ordenes.servicio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.software2uis.msv_ordenes.modelo.Pedido;

public class EntregaServiceCasosExtremosTest {

    @InjectMocks
    private EntregaService entregaService;

    @Mock
    private EntregaRepositorio entregaRepositorio;

    @Test
    public void testCalculateEstimatedDelivery_withLongDistance() {
        // Inicializa los mocks
        MockitoAnnotations.openMocks(this);

        Pedido pedido = new Pedido();
        pedido.setDireccionEnvio("Distant Address");

        // Mock de la distancia extrema
        when(entregaService.getDistanceFromAddress(pedido.getDireccionEnvio())).thenReturn(5000.0);

        // Calcula la fecha estimada de entrega
        LocalDate estimatedDate = entregaService.calculateEstimatedDelivery(pedido);

        // Fecha esperada basada en la distancia extrema
        LocalDate expectedDate = LocalDate.now().plusDays(102);

        // Asegura que la fecha estimada coincida con la fecha esperada
        assertEquals(expectedDate, estimatedDate, "La fecha de entrega estimada no coincide con la fecha esperada.");
    }
}

